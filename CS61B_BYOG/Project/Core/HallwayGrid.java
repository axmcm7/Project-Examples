package byog.Core;
import java.util.Random;

public class HallwayGrid {
    final int MAPHEIGHT = 30;
    final int MAPWIDTH = 80;

    int numVertHalls;
    int numHorizHalls;

    int firstVertHallXPos;
    int firstHorizHallYPos;

    int[] vertXArray;
    int[] horizYArray;

    long seed;

    Random r = new Random(seed);


    public HallwayGrid(long seed) {
        //randomly sets the number of hallways in both directions
        this.numVertHalls = numVertHalls();
        this.numHorizHalls = numHorizHalls();

        //initializes arrays of the midpoints of the hallways
        vertXArray = new int[numVertHalls];
        horizYArray = new int[numHorizHalls];

        //initializes the starting positions of the first hallways in both directions
        this.firstVertHallXPos = firstVertHallXPos();
        this.firstHorizHallYPos = firstHorizHallYPos();

        this.seed = seed;
        //determines the locations of all subsequent vertical hallways
        for (int i = 1; i < numVertHalls; i++) {
            Random s = new Random(r.nextLong());
            int add = RandomUtils.uniform(s, 5, 12);
            vertXArray[i] = add + vertXArray[i - 1];
        }

        //determines the locations of all subsequent horizontal hallways
        for (int i = 1; i < numHorizHalls; i++) {
            Random t = new Random(r.nextLong());
            int add = RandomUtils.uniform(t, 2, 4);
            horizYArray[i] = add + horizYArray[i - 1];
        }
    }

    private int numVertHalls() {
        Random t = new Random();
        return RandomUtils.uniform(r, 5, 10) + RandomUtils.uniform(t, 1, 5);
    }

    private int numHorizHalls() {
        Random t = new Random();
        return RandomUtils.uniform(r, 3, 7) + RandomUtils.uniform(t, 1, 4);
    }

    private int firstVertHallXPos() {
        //Random r = new Random(seed);
        vertXArray[0] = RandomUtils.uniform(r, 5, 15);
        return vertXArray[0];
    }

    private int firstHorizHallYPos() {
        //Random r = new Random(seed);
        horizYArray[0] = RandomUtils.uniform(r, 3, 7);
        return horizYArray[0];
    }
}
