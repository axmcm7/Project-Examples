package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.Core.RandomUtils;
import java.util.Random;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        /*
        for (int x = 10; x < 50; x += 1) {
            for (int y = 0; y < 5; y += 1) {
                world[x][y] = Tileset.FLOWER;
            }
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.GRASS;
            }
            for (int y = 10; y < 15; y += 1) {
                world[x][y] = Tileset.WATER;
            }
            for (int y = 15; y < 20; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }
        */


        //horizontal hallway on the bottom
        for (int x = 1; x < 79; x += 1) {
            for (int y = 2; y < 3; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }
        //horizontal hallway on the top
        for (int x = 1; x < 79; x += 1) {
            for (int y = 27; y < 28; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }
        //vertical hallway on the left
        for (int x = 2; x < 3; x += 1) {
            for (int y = 1; y < 29; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }
        //vertical hallway on the right
        for (int x = 77; x < 78; x += 1) {
            for (int y = 1; y < 29; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }

        //draws ? vertical hallways, starting from the leftmost vertical hallways
        //permanent X bounds are [4, 76)
        Random r = new Random();
        //int randomNumVerts = RandomUtils.uniform(r, 10, 10);
        for (int x = 4; x < 76; x += 7) {
            for (int y = 1; y < 29; y += 1) {
                world[x][y] = Tileset.SAND;
            }
        }

        //draws ? horizontal hallways, starting from the bottommost horizontal hallway
        //permanent Y bounds are [4, 26)
        Random r2 = new Random();
        //int randomNumHorizs = RandomUtils.uniform(r2, 10, 10);
        for (int y = 4; y < 26; y += 2) {
            for (int x = 1; x < 79; x++) {
                world[x][y] = Tileset.SAND;
            }
        }


        //draws a randomly located 3x3 room
        //Random r2 = new Random();
        int randomXPos = RandomUtils.uniform(r2, 77) + 2;
        Random r3 = new Random();
        int randomYPos = RandomUtils.uniform(r3, 27) + 2;

        for (int y = randomYPos; y < 4; y++) {
            for (int x = randomXPos; x < 4; x++) {
                world[x][y] = Tileset.WATER;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }


}