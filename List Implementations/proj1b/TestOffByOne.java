import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {

        char char1 = 'a';
        char char2 = 'b';

        assertEquals(offByOne.equalChars(char1, char2), true);
    }

    @Test
    public void testEqualChars2() {

        char char1 = 'c';
        char char2 = 'c';

        assertEquals(offByOne.equalChars(char1, char2), false);
    }

    @Test
    public void testEqualChars3() {

        char char1 = 'D';
        char char2 = 'e';

        assertEquals(offByOne.equalChars(char1, char2), false);
    }

    @Test
    public void testEqualChars4() {

        char char1 = 'g';
        char char2 = 'f';

        assertEquals(offByOne.equalChars(char1, char2), true);
    }

    @Test
    public void testEqualChars5() {

        char char1 = 'a';
        char char2 = 'z';

        assertEquals(offByOne.equalChars(char1, char2), false);
    }

    @Test
    public void testEqualChars6() {

        char char1 = '$';
        char char2 = '%';

        assertEquals(offByOne.equalChars(char1, char2), true);
    }
}
