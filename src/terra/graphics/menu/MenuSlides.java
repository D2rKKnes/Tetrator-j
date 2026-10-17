package terra.graphics.menu;

import arc.math.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.world.*;
import terra.content.*;

//Original code from PU
public class MenuSlides{
    public static MenuSlide

    stone = new MenuSlide(false){
        @Override
        protected void generate(Tiles tiles){
            boolean tech = Mathf.chance(0.25);
            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    Block floor = Blocks.basalt;
                    Block overlay = Blocks.air;
                    Block wall = Blocks.air;

                    if(Simplex.noise2d(seed + 1, 3, 0.5, 1/20.0, x, y) > 0.4){
                        floor = Blocks.stone;
                    }

                    if(Simplex.noise2d(seed + 1, 3, 0.25, 1/20.0, x, y) > 0.5){
                        wall = Blocks.stoneWall;
                    }

                    if(Simplex.noise2d(seed + 1, 3, 0.125, 1/20.0, x, y) > 0.5){
                        wall = Blocks.carbonWall;
                    }

                    if(tech){
                        int mx = x % 10, my = y % 10;
                        int sclx = x / 10, scly = y / 10;
                        if(Simplex.noise2d(seed + 2, 2, 1f / 10f, 1f, sclx, scly) > 0.6f && (mx == 0 || my == 0 || mx == 9 || my == 9)){
                            floor = Blocks.darkPanel3;
                            if(Mathf.dst(mx, my, 5, 5) > 6f){
                                floor = Blocks.darkPanel4;
                            }

                            if(wall != Blocks.air && Mathf.chance(0.7)){
                                wall = Blocks.darkMetal;
                            }
                        }
                    }

                    if(wall == Blocks.air && (floor == Blocks.basalt || floor == Blocks.stone) && Math.abs(0.5f - noise2d(seed + 1, 3, 0.6, 1/20.0, x, y)) > 0.31f){
                        overlay = TerraEnvironmentBlocks.oreSilver;
                    }

                    setTile(x, y, wall, overlay, floor);
                }
            }
        }
    },

    grass = new MenuSlide(false){
        @Override
        protected void generate(Tiles tiles){
            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    Block floor = Blocks.metalFloor4;
                    Block overlay = Blocks.air;
                    Block wall = Blocks.air;

                    if(tiles.get(x, y) == null){
                        floor = Blocks.metalFloor;
                        if(Mathf.chance(0.1)){
                            floor = Blocks.metalFloor;
                        }
                    }

                    boolean c1, c2;
                    c1 = x % 10 == 0;
                    c2 = y % 10 == 0;
                    if(c1 || c2){
                        floor = Blocks.metalFloorDamaged;

                        if(c1 && c2){
                            setTile(x + 1, y + 1, Blocks.air, Blocks.air, Blocks.metalFloor4);
                            setTile(x + 2, y + 1, Blocks.air, Blocks.air, Blocks.metalFloor4);
                        }

                        if(Simplex.noise2d(seed + 3, 2, 0.6, 1/22.0, x, y) > 0.5){
                            wall = Blocks.stoneWall;

                            if(Simplex.noise2d(seed, 2, 0.6, 1/22.0, x, y) > 0.55){
                                wall = Blocks.shrubs;
                            }
                        }
                    }

                    if(Simplex.noise2d(seed, 2, 0.6, 1/22.0, x, y) > 0.5){
                        floor = Blocks.grass;

                        if(Mathf.chance(0.09)){
                            wall = Blocks.pine;
                        }
                    }

                    setTile(x, y, wall, overlay, floor);
                }
            }
        }
    },

    warzone = new MenuSlide(true){
        @Override
        protected void generate(Tiles tiles){
            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    Block floor = Blocks.stone;
                    Block wall = Blocks.air;

                    setTile(x, y, wall, Blocks.air, floor);
                }
            }
        }
    };
}
