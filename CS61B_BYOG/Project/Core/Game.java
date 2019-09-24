package byog.Core;

//import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    String initialMovement;
    Long seed;
    MapGenerator mapGen;
    TETile[][] world;
    boolean gameOver;
    String language = "English";
    String Name;

    //shows the main menu
    private void showMainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Arial", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        //StdDraw.clear(new Color(0, 0, 0));
        StdDraw.enableDoubleBuffering();

        if (language == "English") {
            StdDraw.text(40.0, 20, "CS61B: THE GAME");
            StdDraw.text(40.0, 15, "New Game (N)");
            StdDraw.text(40.0, 12, "Load Game (L)");
            StdDraw.text(40.0, 9, "Quit (Q)");
            //StdDraw.text(40.0, 6, "Change Language (X)");
            StdDraw.text(40.0, 6, "French (F)");
        } else if (language == "French") {
            StdDraw.text(40.0, 20, "L'INFORMATIQUE 61B: LE JEU");
            StdDraw.text(40.0, 15, "Nouvel Jeu (N)");
            StdDraw.text(40.0, 12, "Reprendre votre jeu précédent (L)");
            StdDraw.text(40.0, 9, "Quitter l'application (Q)");
            //StdDraw.text(40.0, 6, "Changer de langue (X)");
            StdDraw.text(40.0, 6, "Anglais (E)");
        }

        StdDraw.show();

        /*
        if (StdDraw.isMousePressed() && language == "French") {
              if (StdDraw.mouseX() > 30 && StdDraw.mouseX() < 50 && StdDraw.mouseY() < 7 && StdDraw.mouseY() > 5) {
                    language = "English";
                    showMainMenu();
                    playWithKeyboard();
                }
            }
            */
    }

    /**
     * PHASE 2 TASK
     * Method used for playing a fresh game. The game should start from the main menu.
     * When your playWithKeyboard() method is run, your game must display a Main Menu
     * that provides at LEAST the option to start a new game, load a previously saved game,
     * and quit the game. The Main Menu should be navigable only using the keyboard,
     * using N for “new game”, L for “load game”, and Q for quit.
     */
    public void playWithKeyboard() {
        gameOver = false;

        showMainMenu();

        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char userInput = StdDraw.nextKeyTyped();

                if (userInput == 'N' || userInput == 'n') {
                    //starts new game
                    StdDraw.pause(1000);
                    solicitSeedValue();
                    chooseName();

                    mapGen = new MapGenerator(this.seed, "", this.Name);
                    mapGen.drawMap();

                    solicitMovement();
                    gameOver = true;
                } else if (userInput == 'L' || userInput == 'l') {

                    this.mapGen = loadMap();
                    mapGen.drawMap();
                    solicitMovement();

                    gameOver = true;
                } else if (userInput == 'Q' || userInput == 'q') {
                    //quits the game
                    gameOver = true;
                    StdDraw.clear();
                    if (language == "English") {
                        StdDraw.text(40.0, 20, "Thanks for playing!");
                    } else if (language == "French") {
                        StdDraw.text(40.0, 20, "Merci d'avoir joué!");
                    }
                    StdDraw.show();
                    StdDraw.pause(5000);
                    System.exit(0);
                } else if (userInput == 'E' || userInput == 'e') {
                    //showLanguageMenu();
                    language = "English";
                    showMainMenu();
                } else if (userInput == 'F' || userInput == 'f') {
                    language = "French";
                    showMainMenu();
                }
            }
        }
    }

    public void solicitSeedValue() {
        StdDraw.clear();
        if (language == "English") {
            StdDraw.text(40.0, 20, "Please enter a seed number, then press S");
        } else if (language == "French") {
            StdDraw.text(40.0, 20, "S'il vous plaît entrez un numéro de graine, puis appuyez sur S");
        }
        StdDraw.show();
        String userInput = "";

        while (!Pattern.matches(".*[sS]", userInput)) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            userInput += String.valueOf(key);
        }

        StdDraw.pause(500);
        String seedString = userInput.split("[sS]", 2)[0];
        this.seed = Long.parseLong(seedString);
        if (language == "English") {
            StdDraw.text(40.0, 10, "Your seed is: " + seed);
        } else if (language == "French") {
            StdDraw.text(40.0, 10, "Votre numérode de graine: " + seed);
        }
        StdDraw.show();
        StdDraw.pause(2000);
    }

    public void chooseName() {
        String with1 = "";
        this.Name = "";

        StdDraw.clear();
        if (language == "English") {
            StdDraw.text(40.0, 20.0, "Please enter your name, then press 1 to start");
        } else if (language == "French") {
            StdDraw.text(40.0, 20.0, "Entrez votre nom, puis appuyez sur 1 pour démarrer");
        }

        StdDraw.show();

        while (!Pattern.matches(".*1", with1)) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            with1 += String.valueOf(key);

        }

        for (int i = 0; i < with1.length() - 1; i++) {
            this.Name += with1.charAt(i);
        }

        if (language == "English") {
            StdDraw.text(40.0, 10.0, "Welcome " + this.Name + "!");
        } else if (language == "French") {
            StdDraw.text(40.0, 10.0, "Bienvenue " + this.Name + "!");
        }

        StdDraw.show();
        StdDraw.pause(3000);
    }

    public void solicitMovement() {
        String userInput = "";
        String tileType = "";
        TETile tileLoc;

        while (!Pattern.matches(".*:[qQ]", userInput)) {

            //Konami Code
            if (Pattern.matches(".*wwssadadba", userInput)) {
                StdDraw.clear();
                if (language == "English") {
                    StdDraw.text(40, 20, "You beat CS61B: The Game!");
                } else if (language == "French") {
                    StdDraw.text(40, 20, "Vous avez gagné L'Informatique 61B: Le Jeu!");
                }
                StdDraw.show();
                StdDraw.pause(5000);
                System.exit(0);
            }

            if (StdDraw.mouseX() + StdDraw.mouseY() != 0.0 && StdDraw.mouseX() > -1 && StdDraw.mouseX() < 80 && StdDraw.mouseY() > -1 && StdDraw.mouseY() < 30) {
                tileLoc = mapGen.world[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()];
                if (tileLoc == mapGen.floors) {
                    if (language == "English") {
                        tileType = "Floor";
                    } else if (language == "French") {
                        tileType = "Le sol";
                    }
                } else if (tileLoc == mapGen.walls) {
                    if (language == "English") {
                        tileType = "Wall";
                    } else if (language == "French") {
                        tileType = "Le mur";
                    }
                } else if (tileLoc  != mapGen.walls &&  tileLoc != mapGen.floors &&  tileLoc != mapGen.player) {
                    if (language == "English") {
                        tileType = "Outside";
                    } else if (language == "French") {
                        tileType = "l'extérieur";
                    }
                } else if (mapGen.world[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()] == mapGen.player) {
                    tileType = "Player";
                }
                mapGen.overlayHUD(tileType);
            }

            if (StdDraw.mouseX() > -1 && StdDraw.mouseX() < 80 && StdDraw.mouseY() < 34 && StdDraw.mouseY() > 30) {
                StdDraw.clear();
                StdDraw.enableDoubleBuffering();
                mapGen.refreshMap();
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char key = StdDraw.nextKeyTyped();
            userInput += String.valueOf(key);

            if (key == 'W' || key == 'w') {
                mapGen.moveUp();
            } else if (key == 'S' || key == 's') {
                mapGen.moveDown();
            } else if (key == 'A' || key == 'a') {
                mapGen.moveLeft();
            } else if (key == 'D' || key == 'd') {
                mapGen.moveRight();
            }
        }

        StdDraw.clear();
        if (language == "English") {
            StdDraw.text(40.0, 20, "Thanks for playing!");
        } else if (language == "French") {
            StdDraw.text(40.0, 20, "Merci d'avoir joué!");
        }
        StdDraw.show();
        saveMap(this.mapGen);
        StdDraw.pause(5000);
        System.exit(0);
    }

    /**
     * PHASE 1 TASK
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //  Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        String[] withoutN;
        String[] withoutS;
        String[] withoutL;
        String[] withoutQ;
        String numberString;

        if (Character.toLowerCase(input.charAt(0)) == 'n') {
            withoutN = input.split("[nN]", 2);
            withoutS = withoutN[1].split("[sS]", 2);
            numberString = withoutS[0];
            try {
                initialMovement = withoutS[1];
                if (Pattern.matches(".*:[qQ].*", initialMovement)) {
                    withoutQ = initialMovement.split(":[qQ]", 2);
                    initialMovement = withoutQ[0];
                    this.seed = Long.parseLong(numberString);
                    mapGen = new MapGenerator(this.seed, initialMovement, this.Name);
                    saveMap(this.mapGen);
                } else {
                    this.seed = Long.parseLong(numberString);
                    mapGen = new MapGenerator(this.seed, initialMovement, this.Name);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                initialMovement = "";
                this.seed = Long.parseLong(numberString);
                mapGen = new MapGenerator(this.seed, initialMovement, this.Name);
            }
        } else if (Character.toLowerCase(input.charAt(0)) == 'l') {
            this.mapGen = loadMap();
            withoutL = input.split("[lL]", 2);
            try {
                initialMovement = withoutL[1];
                if (Pattern.matches(".*:[qQ].*", initialMovement)) {
                    withoutQ = initialMovement.split(":[qQ]", 2);
                    initialMovement = withoutQ[0];
                    mapGen.playerMovement();
                    saveMap(this.mapGen);
                } else {
                    mapGen.playerMovement();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                initialMovement = "";
            }
        }

        /*this.seed = Long.parseLong(numberString);
        MapGenerator map = new MapGenerator(this.seed, initialMovement);
        mapGen = map;
        */
        TETile[][] finalWorldFrame = mapGen.world;
        world = mapGen.world;
        //System.out.println(finalWorldFrame[5][5].description());

        //mapGen.drawMap();
        return finalWorldFrame;
    }

    public static MapGenerator loadMap() {
        File f = new File("./map.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                MapGenerator loadMap = (MapGenerator) os.readObject();
                os.close();
                return loadMap;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        Random r = new Random();
        Long seed = r.nextLong();
        return new MapGenerator(seed, "", "Player");
    }

    public static void saveMap(MapGenerator mapGen) {
        File f = new File("./map.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(mapGen);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playWithKeyboard();
        //g1.lplayWithInputString("n12345ddwa:q");
        //g1.mapGen.drawMap();
        //g1.playWithKeyboard();
    }
}
