public class OffByN implements CharacterComparator {
    private int n;

    public OffByN(int N) {
        this.n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int difference = Math.abs(x - y);
        if (difference == this.n) {
            return true;
        }
        return false;
    }
}
