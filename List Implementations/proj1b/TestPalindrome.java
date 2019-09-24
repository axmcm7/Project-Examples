import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        Boolean actual = false;
        assertEquals(palindrome.isPalindrome("cats"), actual);
    }

    @Test
    public void testIsPalindrome2() {
        Boolean actual = true;
        assertEquals(palindrome.isPalindrome("racecar"), actual);
    }

    @Test
    public void testIsPalindromeSpecial() {
        Boolean actual = true;

        assertEquals(palindrome.isPalindrome("flake", offByOne), actual);
    }

    @Test
    public void testIsPalindromeSpecial2() {
        Boolean actual = true;

        assertEquals(palindrome.isPalindrome("cab", offByOne), actual);
    }

    @Test
    public void testIsPalindromeSpecial3() {
        Boolean actual = false;

        assertEquals(palindrome.isPalindrome("racecar", offByOne), actual);
    }

    @Test
    public void testIsPalindromeSpecial4() {
        Boolean actual = false;

        assertEquals(palindrome.isPalindrome("trances", offByOne), actual);
    }

    @Test
    public void testBindingEdgeCase() {
        assertEquals(palindrome.isPalindrome("binding", offByN), true);
    }
}
