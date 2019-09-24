public class Palindrome {

    /**
     * returns a Deque where the characters appear in the same order as in the string.
     */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> returned = new ArrayDeque<>();
        for (int i = word.length() - 1; i >= 0; i--) {
            returned.addFirst(word.charAt(i));
        }
        return returned;
    }

    /**
     * returns true if the given word is a palindrome, and false otherwise
     */
    public boolean isPalindrome(String word) {
        Deque wordDeque = wordToDeque(word);
        ArrayDeque<Character> palindrome = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            palindrome.addFirst(word.charAt(i));
        }

        for (int i = 0; i < word.length(); i++) {
            if (wordDeque.get(i) == palindrome.get(i)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * return true if the word is a palindrome according to the character
     * comparison test provided by the CharacterComparator passed in as argument cc
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque wordDeque = wordToDeque(word);
        ArrayDeque<Character> palindrome = new ArrayDeque<>();

        for (int i = 0; i < word.length(); i++) {
            palindrome.addFirst(word.charAt(i));
        }

        for (int i = 0; i < word.length(); i++) {
            char character = (char) wordDeque.get(i);
            if (cc.equalChars(character, palindrome.get(i))) {
                continue;
            } else if ((word.length() % 2 == 1) && i == (word.length() - 1) / 2) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
