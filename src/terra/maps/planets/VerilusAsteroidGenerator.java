package terra.maps.planets;

import terra.content.TerraEnvironmentBlocks;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.generators.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class VerilusAsteroidGenerator extends BlankPlanetGenerator{
    public int min = 22, max = 35, octaves = 2, foct = 3;
    public float radMin = 12f, radMax = 60f, persistence = 0.4f, scale = 30f, mag = 0.46f, thresh = 1f;
    public float fmag = 0.5f, fscl = 50f, fper = 0.6f;
    public float stoneChance = 0f, iceChance = 0.4f, carbonChance = 0.35f;

    public float thoriumScl = 1f, leadScale = 1f, graphiteScale = 1f;

    @Nullable Rand rand;
    int seed = rand.random(30000);

    {
        defaultLoadout = Loadouts.basicNucleus;
    }

    void asteroid(int ax, int ay, int radius){
        Floor floor = (
            rand.chance(iceChance) ? Blocks.ice :
            rand.chance(carbonChance) ? Blocks.carbonStone :
            Blocks.stone
        ).asFloor();

        for(int x = ax - radius; x <= ax + radius; x++){
            for(int y = ay - radius; y <= ay + radius; y++){
                if(tiles.in(x, y) &&  Mathf.dst(x, y, ax, ay) / radius + Simplex.noise2d(seed, octaves, persistence, 1f / scale, x, y) * mag < thresh){
                    tiles.getn(x, y).setFloor(floor);
                }
            }
        }
    }

    @Override
    public void generate(){
        seed = state.rules.sector.planet.id;
        int sx = width/2, sy = height/2;
        rand = new Rand(seed);

        Floor background = Blocks.empty.asFloor();

        tiles.eachTile(t -> t.setFloor(background));

        //spawn asteroids
        asteroid(sx, sy, rand.random(30, 50));

        int amount = rand.random(min, max);
        for(int i = 0; i < amount; i++){
            float radius = rand.random(radMin, radMax), ax = rand.random(radius, width - radius), ay = rand.random(radius, height - radius);

            asteroid((int)ax, (int)ay, (int)radius);
        }

        //tiny asteroids
        int smalls = rand.random(min, max) * 3;
        for(int i = 0; i < smalls; i++){
            float radius = rand.random(1, 8), ax = rand.random(radius, width - radius), ay = rand.random(radius, height - radius);

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

        //walls at insides
        pass((x, y) -> {
            if(floor == background || Ridged.noise2d(seed + 1, x, y, 4, 0.7f, 1f / 60f) > 0.45f || Mathf.within(x, y, sx, sy, 20 + Ridged.noise2d(seed, x, y, 3, 0.5f, 1f / 30f) * 6f)) return;

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
            if(floor == Blocks.carbonStone || floor == Blocks.stone){
                chanc = 0.002f;
            }else if(floor == Blocks.ice){
                chanc = 0.003f;
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

        //titanium
        pass((x, y) -> {
            if(floor != Blocks.stone) return;
            int i = 4;

            if(Math.abs(0.5f - noise(x, y + i*999 - x*1.5f, 2, 0.65, (60 + i * 2))) > 0.26f * 1f){
                ore = Blocks.oreTitanium;
            }
        });

        int spawnSide = rand.random(3);
        int sizeOffset = width / 2 - 1;
        tiles.getn(sizeOffset * Geometry.d8edge[spawnSide].x + width/2, sizeOffset * Geometry.d8edge[spawnSide].y + height/2).setOverlay(Blocks.spawn);

        Schematics.placeLaunchLoadout(sx, sy);

        state.rules.planetBackground = new PlanetParams(){{
            planet = sector.planet;
            zoom = 1f;
            camPos = new Vec3(1.2388899f, 1.6047299f, 2.4758825f);
        }};

        state.rules.dragMultiplier = 0.5f; //yes, space actually has 0 drag but true 0% drag is very annoying
        state.rules.borderDarkness = false;
        state.rules.waves = true;
        state.rules.dropZoneRadius = 100f;
        state.rules.waveSpacing = 3 * Time.toMinutes;

        //TODO ???
        //state.rules.hiddenBuildItems.addAll(Items.plastanium, Items.surgeAlloy);
        //TODO maybe make this on by default everywhere
        state.rules.showSpawns = true;
        //TODO better wavegen, do it by hand even
        state.rules.spawns = Waves.generate(0.5f, rand, false, true, false);
    }

    @Override
    public int getSectorSize(Sector sector){
        return 600;
    }
}
