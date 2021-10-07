public interface FeistelCipher {
    int[] RESHUFFLE = new int[]{4, 1, 3, 2};
    int[] KEYS = new int[]{354453, 256453, 191146};
    int ALPHABET_SIZE = 65536;
    default int secretFunc(int key, int value) {
        return (key * value) % ALPHABET_SIZE;
    }
}
