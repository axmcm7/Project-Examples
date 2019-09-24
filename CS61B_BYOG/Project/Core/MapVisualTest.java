package byog.Core;
import java.util.Random;
/**
 * PHASE 1 TASK
 * A MapGenerator "launcher"
 * Calls methods in MapGenerator directly, supplying seeds manually,
 * without collection from Game or Main.
 */
public class MapVisualTest {
    long num1 =  2113775363165694379L;
    long num2 =  7341909481878015308L;

    Random q = new Random(num1);
    Random r = new Random(num2);

    public static void main(String[] args) {
        MapVisualTest test = new MapVisualTest();
        long next = test.q.nextLong();
        long next2 = test.r.nextLong();
    }
}
