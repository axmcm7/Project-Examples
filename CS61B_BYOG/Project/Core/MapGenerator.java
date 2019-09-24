package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
//import edu.princeton.cs.introcs.StdDraw;

//import byog.Core.RandomUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * PHASE 1 TASK
 * Generates a random map
 * https://www.youtube.com/playlist?list=PL8FaHk7qbOD6REWjsJd5jz9fpXO5_3ebY
 */
public class MapGenerator implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    TETile[][] world;
    TERenderer ter;
    long seed;
    Random r;
    String initialMovement;
    int initialXPos;
    int initialYPos;
    int curXPos;
    int curYPos;
    TETile player;
    TETile floors; //rooms and hallways
    TETile walls;
    String Name;

    //constructor for playWithInputString();
    public MapGenerator(long seed, String initialMovement, String Name) {
        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        this.seed = seed;
        this.initialMovement = initialMovement;
        this.r = new Random(this.seed);

        this.Name = Name;

        //set the theme
        int randomTheme = RandomUtils.uniform(r, 0, 3);

        //desert/mountain theme
        if (randomTheme == 0) {
            player = Tileset.PLAYER;
            floors = Tileset.SAND;
            walls = Tileset.MOUNTAIN;
        }
        //forest theme
        else if (randomTheme == 1) {
            player = Tileset.FLOWER;
            floors = Tileset.GRASS;
            walls = Tileset.TREE;
        }
        //water theme
        else if (randomTheme == 2) {
            player = Tileset.WATERPLAYER;
            floors = Tileset.WATERPATH;
            walls = Tileset.WATER;
        }

        //initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        createHallwayGrid();
        createRooms();
        createWall();
        initializePosition();
        playerMovement();


    }

    public void drawMap() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    public void overlayHUD(String tileType) {

        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.text(73, 32, tileType);
        refreshMap();
    }

    public void refreshMap() {
        //ter.renderFrame(world);
        StdDraw.textLeft(6.0, 32.0, this.Name);


        int numXTiles = world.length;
        int numYTiles = world[0].length;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + ter.xOffset, y + ter.yOffset);
            }
        }
        StdDraw.show();
    }

    public void createHallwayGrid() {
        HallwayGrid grid = new HallwayGrid(this.seed);
        Random s = new Random(r.nextLong());
        Random t = new Random(r.nextLong());
        Random u = new Random(r.nextLong());

        //draws the vertical hallway grid, determining the "Up" and "Down" lengths to draw to
        for (int i = 0; i < grid.numVertHalls; i++) {
            int addUp = RandomUtils.uniform(s, 10, 13);
            int addDown = RandomUtils.uniform(t, 10, 13);

            for (int j = 15 - addDown; j < 16 + addUp; j++) {
                world[grid.vertXArray[i]][j] = floors;
            }
        }

        //draws the horizontal hallway grid, determining the "Left" and "Right" lengths to draw to
        for (int i = 0; i < grid.numHorizHalls; i++) {
            int addRight = RandomUtils.uniform(t, 34, 37);
            int addLeft = RandomUtils.uniform(u, 34, 39);

            for (int j = 40 - addLeft; j < 41 + addRight; j++) {
                world[j][grid.horizYArray[i]] = floors;
            }
        }
    }

    public void createRooms() {
        Rooms rooms = new Rooms(seed);
        Random v = new Random(r.nextLong());
        for (int i = 0; i < rooms.numRooms; i++) {

            //intialization of random integers for drawing
            int width = RandomUtils.uniform(v, 3, 6);
            int height = RandomUtils.uniform(v, 2, 6);
            int X = RandomUtils.uniform(v, 2, 71); //bottom left
            int Y = RandomUtils.uniform(v, 2, 21); //bottom left

            if (overlap(width, height, X, Y)) {
                //drawing of randomized conditions
                for (int m = X; m < X + width + 1; m++) {
                    for (int n = Y; n < Y + height + 1; n++) {
                        world[m][n] = floors;
                    }
                }
            }

        }
    }

    private boolean overlap(int width, int height, int X, int Y) {
        int k = 0;

        for (int m = X; m < X + width + 1; m++) {
            for (int n = Y; n < Y + height + 1; n++) {
                if (world[m][n].equals(floors)) {
                    k += 1;
                }
            }
        }
        return k > 0;
    }

    public void createWall() {
        for (int x = 1; x < 79; x++) {
            for (int y = 1; y < 29; y++) {
                if (needsWall(x, y)) {
                    world[x][y] = walls;
                }
            }
        }
    }

    private boolean needsWall(int x, int y) {
        if (world[x][y] != floors) {
            if (world[x + 1][y].equals(floors)) {
                return true;
            }
            if (world[x - 1][y].equals(floors)) {
                return true;
            }
            if (world[x][y + 1].equals(floors)) {
                return true;
            }
            if (world[x][y - 1].equals(floors)) {
                return true;
            }
            if (world[x + 1][y + 1].equals(floors)) {
                return true;
            }
            if (world[x - 1][y - 1].equals(floors)) {
                return true;
            }
            if (world[x - 1][y + 1].equals(floors)) {
                return true;
            }
            if (world[x + 1][y - 1].equals(floors)) {
                return true;
            }
        }
        return false;
    }

    public void initializePosition() {
        boolean initialized = false;
        int i = 0;
        while (!initialized) {
            this.initialXPos = RandomUtils.uniform(new Random(r.nextLong() + i), 1, 78);
            this.initialYPos = RandomUtils.uniform(new Random(r.nextLong() + i), 1, 28);

            if (world[initialXPos][initialYPos].equals(floors)) {
                initialized = true;
            }
            i += 1;
        }

        curXPos = initialXPos;
        curYPos = initialYPos;

        world[curXPos][curYPos] = player;

    }

    public void playerMovement() {

        if (initialMovement.equals("")) {
            world[curXPos][curYPos] = floors;
            for (int j = 0; j < initialMovement.length(); j++) {
                if (initialMovement.charAt(j) == 'A' || initialMovement.charAt(j) == 'a') {
                    if (world[curXPos - 1][curYPos].equals(floors)) {
                        curXPos--;
                    }
                } else if (initialMovement.charAt(j) == 'D' || initialMovement.charAt(j) == 'd') {
                    if (world[curXPos + 1][curYPos].equals(floors)) {
                        curXPos++;
                    }
                } else if (initialMovement.charAt(j) == 'W' || initialMovement.charAt(j) == 'w') {
                    if (world[curXPos][curYPos + 1].equals(floors)) {
                        initialYPos++;
                    }
                } else if (initialMovement.charAt(j) == 'S' || initialMovement.charAt(j) == 's') {
                    if (world[curXPos][curYPos - 1].equals(floors)) {
                        curYPos--;
                    }
                }
            }
        }
        world[curXPos][curYPos] = player;
    }

    public void moveDown() {
        if (world[curXPos][curYPos - 1].equals(floors)) {
            world[curXPos][curYPos] = floors;
            world[curXPos][curYPos - 1] = player;
            curYPos--;
            ter.renderFrame(world);
        }
    }

    public void moveUp() {
        if (world[curXPos][curYPos + 1].equals(floors)) {
            world[curXPos][curYPos] = floors;
            world[curXPos][curYPos + 1] = player;
            curYPos++;
            ter.renderFrame(world);
        }
    }

    public void moveLeft() {

        if (world[curXPos - 1][curYPos].equals(floors)) {
            world[curXPos][curYPos] = floors;
            world[curXPos - 1][curYPos] = player;
            curXPos--;
            ter.renderFrame(world);
        }
    }

    public void moveRight() {
        if (world[curXPos + 1][curYPos].equals(floors)) {
            world[curXPos][curYPos] = floors;
            world[curXPos + 1][curYPos] = player;
            curXPos++;
            ter.renderFrame(world);
        }
    }

    /*public static void main(String[] args) {
        MapGenerator gen1 = new MapGenerator(400, "");
        //gen1.drawMap();
        MapGenerator gen2 = new MapGenerator(400, "a");
        //gen2.drawMap();
        System.out.println(gen1.initialXPos + " " + gen1.initialYPos);
        System.out.println(gen2.initialXPos + " " + gen2.initialYPos);
    }*/

}