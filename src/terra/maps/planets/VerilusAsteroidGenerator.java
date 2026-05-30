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
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class VerilusAsteroidGenerator extends BlankPlanetGenerator{
    public int min = 26, max = 34, octaves = 2, foct = 3;
    public float radMin = 12f, radMax = 61f, persistence = 0.42f, scale = 30f, mag = 0.76f, thresh = 1f;
    public float fmag = 0.5f, fscl = 30f, fper = 0.6f;
    public float stoneChance = 0f, iceChance = 0.4f, carbonChance = 0.6f;

    public float thoriumScl = 1f, leadScale = 1f, graphiteScale = 1f;

    @Nullable Rand rand;
    int seed;

    {
        defaultLoadout = Schematics.readBase64("bXNjaAF4nBWLMQ6AIBAEF0IstPMfPMUXGIsTryBBjtzRGf8uJpOpZhAQBpVuRkiijDlJ7Vz7Rg3+ebFcbElz61kqgKnQycXg98Nh7axK8f+iSSHNNhL3M/QBaAwXkg==");
    }

    void asteroid(int ax, int ay, int radius){
        Floor floor = (
            rand.chance(iceChance) ? Blocks.ice :
            rand.chance(carbonChance) ? Blocks.carbonStone :
            Blocks.stone
        ).asFloor();

        for(int x = ax - radius; x <= ax + radius; x++){
            for(int y = ay - radius; y <= ay + radius; y++){
                if(!tiles.in(x, y)) continue;
                float n1 = Simplex.noise2d(seed, 1, 0.0, 1f / 95f, x + 10, y + 10) * 21f;
                float n2 = Simplex.noise2d(seed + 1, 1, 0.0, 1f / 16f, x + 10, y + 10) * 11f;
                float n3 = Simplex.noise2d(seed + 2, 1, 0.0, 1f / 7f,  x + 10, y + 10) * 4f;
                float distortion = (n1 + n2 + n3) / 21f;
                if(Mathf.dst(x, y, ax, ay) / radius + distortion < thresh){
                    tiles.getn(x, y).setFloor(floor);
                }
            }
        }
    }

    void asteroid(int ax, int ay, int rad, Floor floor) {
        for (int x = ax - rad; x <= ax + rad; x++) {
            for (int y = ay - rad; y <= ay + rad; y++) {
                if(!tiles.in(x, y)) continue;
                float n1 = Simplex.noise2d(seed, 1, 0.0, 1f / 95f, x + 10, y + 10) * 21f;
                float n2 = Simplex.noise2d(seed + 1, 1, 0.0, 1f / 16f, x + 10, y + 10) * 11f;
                float n3 = Simplex.noise2d(seed + 2, 1, 0.0, 1f / 7f,  x + 10, y + 10) * 4f;
                float distortion = (n1 + n2 + n3) / 21f;
                if(Mathf.dst(x, y, ax, ay) / radius + distortion < thresh){
                    tiles.getn(x, y).setFloor(floor);
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
        asteroid(sx, sy, rand.random(34, 68), Blocks.stone.asFloor());

        float radr = 170f + Mathf.random(10f, 50f);
        float anglr = Mathf.random(360f);
        int xr = sx + (int)(Mathf.cosDeg(anglr) * radr);
        int yr = sy + (int)(Mathf.sinDeg(anglr) * radr);

        //special asteroid for core zone also is always stone
        asteroid(xr, yr, rand.random(34, 68), Blocks.stone.asFloor());

        //spawn asteroids
        int amount = rand.random(min, max);
        for(int i = 0; i < amount; i++){
            float radius = rand.random(radMin, radMax), ax = rand.random(radius, width - radius), ay = rand.random(radius, height - radius);

            asteroid((int)ax, (int)ay, (int)radius);
        }

        //tiny asteroids
        int smalls = rand.random(min, max) * 3;
        for(int i = 0; i < smalls; i++){
            float radius = rand.random(6, 16), ax = rand.random(radius, width - radius), ay = rand.random(radius, height - radius);

            asteroid((int)ax, (int)ay, (int)radius);
        }

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
        
        //place core zone
        for (int dxr = -1; dxr <= 1; dxr++) {
            for (int dyr = -1; dyr <= 1; dyr++) {
                Tile tile = world.tile(xr + dxr, yr + dyr);
                if (tile != null) {
                    tile.setFloor(Blocks.coreZone.asFloor());
                }
            }
        }
        //metal around core zone
        for (int x = xr - 2; x <= xr + 2; x++) {
            for (int y = yr - 2; y <= yr + 2; y++) {
                if (x >= xr - 1 && x <= xr + 1 && y >= yr - 1 && y <= yr + 1) {
                    continue;
                }
                Tile tile = world.tile(x, y);
                if (tile != null) {
                    if (Mathf.random() < 0.2f) {
                        tile.setFloor(Blocks.metalFloorDamaged.asFloor());
                    }else if (Mathf.random() < 0.5f) {
                        tile.setFloor(Blocks.metalFloor.asFloor());
                    }
                }
            }
        }

        //walls at insides
        pass((x, y) -> {
            if(floor == background || Ridged.noise2d(seed + 1, x, y, 4, 0.73f, 1.3f / 60f) > 0.45f || Mathf.within(x, y, sx, sy, 20 + Ridged.noise2d(seed, x, y, 3, 0.6f, 1f / 30f) * 6f) || Mathf.within(x, y, xr, yr, 20 + Ridged.noise2d(seed, x, y, 3, 0.5f, 1f / 30f) * 6f)) return;

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
        oreAround(Blocks.oreLead, Blocks.stoneWall, 3, 70f, 0.6f * leadScale);

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

            if(Math.abs(0.5f - noise(x, y + i*999 - x*1.5f, 2, 0.65, (60 + i * 2))) > 0.26f * 1f){
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
