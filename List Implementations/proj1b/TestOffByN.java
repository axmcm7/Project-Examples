import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {

    static OffByN offByN = new OffByN(5);

    @Test
    public void testEqualChars() {
        char char1 = 'a';
        char char2 = 'f';

        assertEquals(offByN.equalChars(char1, char2), true);
    }

    @Test
    public void testEqualChars2() {
        char char1 = 'f';
        char char2 = 'a';

        assertEquals(offByN.equalChars(char1, char2), true);
    }

    @Test
    public void testEqualChars3() {
        char char1 = 'f';
        char char2 = 'h';

        assertEquals(offByN.equalChars(char1, char2), false);
    }

    static OffByN offByN2 = new OffByN(2);

    @Test
    public void testEqualChars4() {
        char char1 = 'a';
        char char2 = 'c';

        assertEquals(offByN2.equalChars(char1, char2), true);
    }

    @Test
    public void testEqualChars5() {
        char char1 = 'a';
        char char2 = 'd';

        assertEquals(offByN2.equalChars(char1, char2), false);
    }

    @Test
    public void testEqualChars6() {
        char char1 = 'a';
        char char2 = 'e';

        assertEquals(offByN2.equalChars(char1, char2), false);
    }
}
