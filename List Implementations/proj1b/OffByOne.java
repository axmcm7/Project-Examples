public class OffByOne implements CharacterComparator {

    /**
     * returns true for characters that are different by exactly one.
     */
    @Override
    public boolean equalChars(char x, char y) {
        int comparison = Math.abs(x - y);
        if (comparison == 1) {
            return true;
        }
        return false;
    }
}
