package byog.Core;
import java.util.Random;

public class Rooms {
    int numRooms; //to be initialized to a random number
    long seed;

    public Rooms(long seed) {
        this.numRooms = numRooms();
        this.seed = seed;
    }

    public int numRooms() {
        Random r = new Random(seed);
        return RandomUtils.uniform(r, 10, 35);
    }

}
