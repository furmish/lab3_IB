import java.util.Arrays;

public class CipherText implements FeistelCipher {
    private final String cipherText;

    public CipherText(String cipherText) {
        this.cipherText = cipherText;
    }
/*@decryptText() */
    public String decryptText() {
        StringBuilder sb = new StringBuilder();
        int beginIndex = 0;
        for (int i = RESHUFFLE.length * 2 - 1; i < cipherText.length(); i += RESHUFFLE.length * 2) {
            int[] left = cipherText.substring(beginIndex, beginIndex + RESHUFFLE.length).codePoints().toArray();
            int[] right = cipherText.substring(beginIndex + RESHUFFLE.length, i + 1).codePoints().toArray();
            Arrays.stream(decryptText(left, right)).forEach(value -> sb.append((char) value));
            beginIndex = i + 1;
        }
        return sb.toString().trim();
    }

    private int[] decryptText(int[] left, int[] right) {
        for (int i = KEYS.length; i > 0; i--) {
            int[] temp = right;
            right = left;
            left = new int[]{0, 0, 0, 0};
            for (int j = 0; j < left.length; j++) {
                left[j] = temp[j] ^ secretFunc(KEYS[i -1], right[RESHUFFLE[j] - 1]);
            }
        }
        int[] decryptedPart = new int[left.length * 2];
        System.arraycopy(left, 0, decryptedPart, 0, 4);
        System.arraycopy(right, 0, decryptedPart, 4, 4);
        return decryptedPart;
    }

    //    (keys[i - 1] * right[reshuffle[j] - 1]) % 65536
    private int secretFunc(int key, int value) {
        return (key * value) % ALPHABET_SIZE;
    }
}

/*int[] left = decryptText(cipherText.substring(0, 4).codePoints().toArray(), cipherText.substring(4, 8).codePoints().toArray());
        int[] right = decryptText(cipherText.substring(8, 12).codePoints().toArray(), cipherText.substring(12).codePoints().toArray());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(left).forEach(value -> sb.append((char) value));
        Arrays.stream(right).forEach(value -> sb.append((char) value));
        return sb.toString();*/