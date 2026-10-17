package terra.graphics.menu;

import arc.math.*;
import arc.util.noise.*;
import mindustry.*;
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

    metal = new MenuSlide(false){
        @Override
        protected void generate(Tiles tiles){
            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    Block floor = Blocks.metalTiles9;
                    Block overlay = Blocks.air;
                    Block wall = Blocks.air;

                    boolean c3, c4;
                    c3 = x % 14 == 0 || x % 14 == 1 || x % 14 == 13;
                    c4 = y % 14 == 0 || y % 14 == 1 || y % 14 == 13;
                    if(c3 || c4){
                        floor = Blocks.metalTiles1;
                        if(Simplex.noise2d(seed + 3, 2, 0.6, 1/22.0, x, y) > 0.4){
                            wall = Blocks.metalWall1;
                        }
                    }
                    
                    setTile(x, y, wall, overlay, floor);
                }
            }

            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    if(x % 14 == 2 && y % 14 == 2){
                        float chanc = Mathf.random(100);
                        if(chanc >= 75){
                            for(int i = 0; i < 11; i++){
                                for(int j = 0; j < 11; j++){
                                    int px = x + i;
                                    int py = y + j;
                                    if(px < 0 || py < 0 || px >= tiles.width || py >= tiles.height) continue;
        
                                    setTile(px, py, Blocks.air, Blocks.air, Blocks.metalTiles7);
                                }
                            }
                        } else if(chanc >= 60){
                            for(int i = 0; i < 11; i++){
                                for(int j = 0; j < 11; j++){
                                    int px = x + i;
                                    int py = y + j;
                                    if(px < 0 || py < 0 || px >= tiles.width || py >= tiles.height) continue;
        
                                    setTile(px, py, Blocks.air, Blocks.air, Blocks.metalTiles2);
                                }
                            }
                        }
                    }
                }
            }
            
            int lineCount = 18;
            float turnChance = 0.15f;
    
            for(int i = 0; i < lineCount; i++){
                int x2 = Mathf.random(tiles.width);
                int y2 = Mathf.random(tiles.height);
    
                int dx = Mathf.randomBoolean() ? 1 : -1;
                int dy = 0;
                if(Mathf.chance(0.5f)){
                    dy = dx; dx = 0;
                }
    
                int steps = Mathf.random(30, 70);
    
                for(int s = 0; s < steps; s++){
                    if(x2 < 0 || y2 < 0 || x2 >= tiles.width || y2 >= tiles.height) continue;
                    Tile cur = tiles.get(x2, y2);
                    Block wall  = cur != null ? cur.block() : Blocks.air;
                    setTile(x2, y2, wall, Blocks.air, Blocks.metalTiles12);
    
                    if(Mathf.chance(turnChance)){
                        if(dx != 0){
                            dy = Mathf.randomBoolean() ? 1 : -1;
                            dx = 0;
                        } else {
                            dx = Mathf.randomBoolean() ? 1 : -1;
                            dy = 0;
                        }
                    }
    
                    x2 += dx;
                    y2 += dy;
    
                    if(x2 < 2 || y2 < 2 || x2 >= tiles.width - 3 || y2 >= tiles.height - 3) continue;
                }
            }

            for(int x = 0; x < tiles.width; x++){
                for(int y = 0; y < tiles.height; y++){
                    boolean c1, c2;
                    c1 = x % 14 == 2 || x % 14 == 3 || x % 14 == 4;
                    c2 = y % 14 == 2;
                    if(c1 && c2){
                        Tile tile = Vars.world.tiles.get(x, y);
                        if(tile == null) continue;
                        
                        tile.setOverlay(Blocks.cruxRuneOverlay);
                        tile.overlayData = (byte)Mathf.random(109);
                    }
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
