package terra.maps.planets;

import terra.content.*;
import terra.maps.generators.VerilusWaves;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import arc.struct.*;
import mindustry.ai.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.game.Rules;
import mindustry.graphics.g3d.*;
import mindustry.maps.generators.*;
import mindustry.maps.filters.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class VerilusAsteroidGenerator extends BlankPlanetGenerator{
    public int min = 26, max = 34, octaves = 2, foct = 3;
    public float radMin = 12f, radMax = 58f, persistence = 0.42f, scale = 30f, mag = 0.76f, thresh = 1f;
    public float fmag = 0.5f, fscl = 36f, fper = 0.6f;
    public float stoneChance = 0f, iceChance = 0.38f, carbonChance = 0.6f;

    //bigger number - less ore
    public float thoriumScl = 1f, leadScale = 1.2f, graphiteScale = 1f;

    @Nullable Rand rand;
    int seed;

    {
        defaultLoadout = Schematics.readBase64("bXNjaAF4nBWLMQ6AIBAEF0IstPMfPMUXGIsTryBBjtzRGf8uJpOpZhAQBpVuRkiijDlJ7Vz7Rg3+ebFcbElz61kqgKnQycXg98Nh7axK8f+iSSHNNhL3M/QBaAwXkg==");
    }

    void asteroid(int ax, int ay, int rad){
        Floor floor = (
            rand.chance(iceChance) ? Blocks.ice :
            rand.chance(carbonChance) ? Blocks.carbonStone :
            Blocks.stone
        ).asFloor();

        for(int x = ax - rad; x <= ax + rad; x++){
            for(int y = ay - rad; y <= ay + rad; y++){
                if(tiles.in(x, y) && Mathf.dst(x, y, ax, ay) < rad) {
                    tiles.getn(x, y).setFloor(floor);
                }
            }
        }
    }

    void asteroid(int ax, int ay, int rad, Floor floor) {
        for (int x = ax - rad; x <= ax + rad; x++) {
            for (int y = ay - rad; y <= ay + rad; y++) {
                if(tiles.in(x, y) && Mathf.dst(x, y, ax, ay) < rad) {
                    tiles.getn(x, y).setFloor(floor);
                }
            }
        }
    }

    void oreAroundRand(Block ore, Block wall, int radMin, int radMax, float scl, float thresh){
        for(Tile tile : tiles){
            int x = tile.x, y = tile.y;
    
            if(tile.block() == Blocks.air && tile.floor().hasSurface() && noise(x, y + ore.id*999, scl, 1f) > thresh){
                int radius = Mathf.random(radMin, radMax);
    
                boolean found = false;
                outer:
                for(int dx = x - radius; dx <= x + radius; dx++){
                    for(int dy = y - radius; dy <= y + radius; dy++){
                        if(Mathf.within(dx, dy, x, y, radius + 0.001f) && tiles.in(dx, dy) && tiles.get(dx, dy).block() == wall){
                            found = true;
                            break outer;
                        }
                    }
                }
    
                if(found){
                    tile.setOverlay(ore);
                }
            }
        }
    }

    void floorZone(Block zone, Block firstRing, Block secRing, int zoneRad, float damagedChance, float normalChance, int x, int y){
        //place zone
        int zoneRadHalf = Mathf.floor(zoneRad / 2f);
        int offset = 0;
        if (zoneRad % 2 == 0){
            offset = 1;
        }
        for (int xz = -zoneRadHalf + offset; xz <= zoneRadHalf; xz++) {
            for (int yz = -zoneRadHalf + offset; yz <= zoneRadHalf; yz++) {
                Tile tile = world.tile(x + xz, y + yz);
                if (tile != null) {
                    tile.setFloor(zone.asFloor());
                }
            }
        }
        //ring around zone
        int ringRad = zoneRadHalf + 1;
        for (int xr = -ringRad + offset; xr <= ringRad; xr++) {
            for (int yr = -ringRad + offset; yr <= ringRad; yr++) {
                if (xr >= -zoneRadHalf + offset && xr <= zoneRadHalf && yr >= -zoneRadHalf + offset && yr <= zoneRadHalf) {
                    continue;
                }
                Tile tile = world.tile(x + xr, y + yr);
                if (tile != null) {
                    if (Mathf.random() < damagedChance) {
                        tile.setFloor(firstRing.asFloor());
                    }else if (Mathf.random() < normalChance) {
                        tile.setFloor(secRing.asFloor());
                    }
                }
            }
        }
    }

    @Override
    public void generate(){
        seed = Mathf.random(30000);
        int sx = width/2, sy = height/2;
        rand = new Rand(seed);

        Floor background = Blocks.empty.asFloor();

        tiles.eachTile(t -> t.setFloor(background));

        //the center asteroid is always stone
        asteroid(sx, sy, rand.random((int)radMax / 2, (int)radMax), Blocks.stone.asFloor());

        float radr = 170f + Mathf.random(10f, 50f);
        float anglr = Mathf.random(360f);
        int xr = sx + (int)(Mathf.cosDeg(anglr) * radr);
        int yr = sy + (int)(Mathf.sinDeg(anglr) * radr);

        //special asteroid for core zone also is always stone
        asteroid(xr, yr, rand.random((int)radMax / 2, (int)radMax), Blocks.stone.asFloor());

        //spawn asteroids
        int amount = rand.random(min, max);
        for(int i = 0; i < amount; i++){
            float radius = rand.random(radMin, radMax), ax = rand.random(radius + 20, width - radius - 20), ay = rand.random(radius + 20, height - radius - 20);

            asteroid((int)ax, (int)ay, (int)radius);
        }

        //tiny asteroids
        int smalls = rand.random(min, max) * 4;
        for(int i = 0; i < smalls; i++){
            float radius = rand.random(3, 12), ax = rand.random(radius + 12, width - radius - 12), ay = rand.random(radius + 12, height - radius - 12);

            asteroid((int)ax, (int)ay, (int)radius);
        }

        //distort noise
        GenerateFilter.GenerateInput in = new GenerateFilter.GenerateInput();
        
        DistortFilter d1 = new DistortFilter();
        d1.scl = 95; d1.mag = 21; d1.seed = seed + 1;
        DistortFilter d2 = new DistortFilter();
        d2.scl = 16; d2.mag = 11; d2.seed = seed + 2;
        DistortFilter d3 = new DistortFilter();
        d3.scl = 7;  d3.mag = 4;  d3.seed = seed + 3;
        
        in.begin(width, height, (x, y) -> tiles.getn(x, y));
        d1.apply(tiles, in);
        in.begin(width, height, (x, y) -> tiles.getn(x, y));
        d2.apply(tiles, in);
        in.begin(width, height, (x, y) -> tiles.getn(x, y));
        d3.apply(tiles, in);
        
        //random noise stone
        pass((x, y) -> {
            if(floor != background){
                if(Ridged.noise2d(seed, x, y, foct, fper, 1f / fscl) - Ridged.noise2d(seed, x, y, 1, 1f, 5f)/2.7f > fmag){
                    floor = Blocks.stone;
                }
            }
        });

        //thermoxite infection
        pass((x, y) -> {
            if(floor == Blocks.carbonStone){
                if(Simplex.noise2d(seed, 9, 0.815f, 1f / 75f, x, y) > 0.63f){
                    floor = TerraEnvironmentBlocks.carbonizedThermoxite;
                }
            }
        });
        pass((x, y) -> {
            if(floor == TerraEnvironmentBlocks.carbonizedThermoxite){
                if(Simplex.noise2d(seed + 1, 6, 0.63f, 1f / 5f, x, y) > 0.6f){
                    floor = TerraEnvironmentBlocks.thermoxiteCrystal;
                }
            }
        });
        blend(TerraEnvironmentBlocks.thermoxiteCrystal, TerraEnvironmentBlocks.carbonizedThermoxite, 1f);
        pass((x, y) -> {
            if(floor == TerraEnvironmentBlocks.carbonizedThermoxite && rand.chance(0.135)){
                floor = Blocks.carbonStone;
            }
        });

        //core zones
        floorZone(Blocks.coreZone, Blocks.metalFloorDamaged, Blocks.metalFloor, 3, 0.2f, 0.5f, xr, yr);
        floorZone(Blocks.coreZone, Blocks.metalFloorDamaged, Blocks.metalFloor, 4, 0.2f, 0.5f, sx, sy);

        //walls at insides
        pass((x, y) -> {
            if(floor == background || Ridged.noise2d(seed + 1, x, y, 4, 0.73f, 1.3f / 60f) > 0.35f || Mathf.within(x, y, sx, sy, 20 + Ridged.noise2d(seed, x, y, 3, 0.6f, 1f / 30f) * 6f) || Mathf.within(x, y, xr, yr, 20 + Ridged.noise2d(seed, x, y, 3, 0.5f, 1f / 30f) * 6f)) return;

            int radius = 6;
            for(int dx = x - radius; dx <= x + radius; dx++){
                for(int dy = y - radius; dy <= y + radius; dy++){
                    if(Mathf.within(dx, dy, x, y, radius + 0.0001f) && tiles.in(dx, dy) && tiles.getn(dx, dy).floor() == background){
                        return;
                    }
                }
            }

            block = floor.asFloor().wall;
        });

        int ventCount = 0;
        //vents and rfits
        outer:
        for(Tile tile : tiles){
            var floor = tile.floor();
            float chanc = 0f;
            if(floor == Blocks.carbonStone){
                chanc = 0.002f;
            }else if(floor == Blocks.stone){
                chanc = 0.0015f;
            }else if(floor == Blocks.ice){
                chanc = 0.0025f;
            }
            if((floor == Blocks.carbonStone || floor == Blocks.stone || floor == Blocks.ice) && rand.chance(chanc)){
                int radius = 2;
                for(int x = -radius; x <= radius; x++){
                    for(int y = -radius; y <= radius; y++){
                        Tile other = tiles.get(x + tile.x, y + tile.y);
                        if(other == null || (other.floor() != Blocks.carbonStone && other.floor() != Blocks.stone && other.floor() != Blocks.ice) || other.block().solid){
                            continue outer;
                        }
                    }
                }

                Block
                vent = Blocks.stoneVent;

                if(tile.floor() == Blocks.carbonStone){
                    vent = Blocks.carbonVent;
                }else if(tile.floor() == Blocks.stone){
                    vent = Blocks.stoneVent;
                }else if(tile.floor() == Blocks.ice){
                    vent = TerraEnvironmentBlocks.iceRift;
                }

                ventCount ++;
                for(var pos : SteamVent.offsets){
                    Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                    other.setFloor(vent.asFloor());
                }
            }
        }

        //random craters
        pass((x, y) -> {
            if(floor == Blocks.stone && rand.chance(0.02)) floor = Blocks.craters;
        });

        decoration(0.017f);

        //lead generates around stone walls
        oreAroundRand(Blocks.oreLead, Blocks.stoneWall, 2, 4, 70f, 0.6f * leadScale);

        //thorium only generates on beryllic stone and graphitic stone
        ore(Blocks.oreThorium, Blocks.carbonStone, 4f, 0.9f * thoriumScl);

        wallOre(Blocks.carbonWall, Blocks.graphiticWall, 35f, 0.57f * graphiteScale);

        //thermoxite ores
        pass((x, y) -> {
            if(floor == TerraEnvironmentBlocks.carbonizedThermoxite){
                if(Simplex.noise2d(seed, 8, 0.4f, 5f, x, y) > 0.55f){
                    ore = TerraEnvironmentBlocks.oreRawThermoxite;
                }
            }
        });

        pass((x, y) -> {
            if(floor == TerraEnvironmentBlocks.thermoxiteCrystal){
                if(Simplex.noise2d(seed + 4, 8, 0.3f, 5f, x, y) > 0.6f){
                    ore = TerraEnvironmentBlocks.oreThermoxite;
                }
            }
        });

        //titanium
        pass((x, y) -> {
            if(floor != Blocks.stone) return;
            int i = 4;

            if(Math.abs(0.5f - noise(x, y + i*999 - x*1.5f, 3, 0.6, 66)) > 0.26f * 1f){
                ore = Blocks.oreTitanium;
            }
        });

        //decor clusters
        pass((x, y) -> {
            if(block == TerraEnvironmentBlocks.carbonizedThermoxiteWall && rand.chance(0.23) && nearAir(x, y) && !near(x, y, 3, TerraEnvironmentBlocks.carbonizedThermoxiteCluster)){
                block = TerraEnvironmentBlocks.carbonizedThermoxiteCluster;
                ore = Blocks.air;
            }

            if(block == TerraEnvironmentBlocks.thermoxiteWall && rand.chance(0.23) && nearAir(x, y) && !near(x, y, 3, TerraEnvironmentBlocks.thermoxiteCluster)){
                block = TerraEnvironmentBlocks.thermoxiteCluster;
                ore = Blocks.air;
            }
        });

        //spawn
        int spawnSide = rand.random(3);
        int sizeOffset = width / 2 - 10;
        tiles.getn(sizeOffset * Geometry.d8edge[spawnSide].x + width/2, sizeOffset * Geometry.d8edge[spawnSide].y + height/2).setOverlay(Blocks.spawn);

        //core in the center
        Schematics.placeLaunchLoadout(sx, sy);

        state.rules.planetBackground = new PlanetParams(){{
            planet = sector.planet;
            zoom = 1f;
            camPos = new Vec3(1.2388899f, 1.6047299f, 2.4758825f);
        }};

        state.rules.dragMultiplier = 0.7f; //yes, space actually has 0 drag but true 0% drag is very annoying
        state.rules.borderDarkness = false;
        state.rules.waves = true;
        state.rules.dropZoneRadius = 200f;
        state.rules.waveSpacing = 3 * Time.toMinutes;

        //progression
        if(TerraItems.tesseract.unlocked()){
            state.rules.loadout = ItemStack.list(Items.lead, 1500, Items.graphite, 1500, Items.titanium, 1500, Items.silicon, 500, Items.metaglass, 500, Items.thorium, 200);
        } else if(TerraItems.darkSteel.unlocked()){
            state.rules.loadout = ItemStack.list(Items.lead, 1000, Items.graphite, 600, Items.titanium, 500, Items.silicon, 200);
        } else if(TerraItems.titaniumPlate.unlocked()){
            state.rules.loadout = ItemStack.list(Items.lead, 500, Items.graphite, 300, Items.titanium, 200);
        } else {
            state.rules.loadout = ItemStack.list(Items.lead, 100);
        }

        //anuke do something again
        //state.rules.showSpawns = true;
        state.rules.spawns = VerilusWaves.generate(rand);
    }

    @Override
    public int getSectorSize(Sector sector){
        return 600;
    }
}
