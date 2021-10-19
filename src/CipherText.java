import java.util.Arrays;

/**
 * Класс зашифрованного текста с возможнностью дешифрования
 */
public class CipherText extends FeistelCipher {
    private final String cipherText;

    public CipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    /**
     * Дешифрует каждый блок из {@link #BLOCK_SIZE} символов в строке по порядку.
     *
     * @return зашифрованную строку
     */
    public String decryptText() {
        StringBuilder sb = new StringBuilder();
        int beginIndex = 0;
        int endIndex = beginIndex + BLOCK_SIZE / 2;
        for (int i = BLOCK_SIZE - 1; i < cipherText.length(); i += BLOCK_SIZE) {
            int[] left = cipherText.substring(beginIndex, endIndex).codePoints().toArray();
            int[] right = cipherText.substring(endIndex, i + 1).codePoints().toArray();
            Arrays.stream(decryptText(left, right)).forEach(value -> sb.append((char) value));
            beginIndex = i + 1;
            endIndex = beginIndex + BLOCK_SIZE / 2;
        }
        return sb.toString().trim();
    }

    /**
     * Функция дешифрования по алгоритму Фейстеля
     *
     * @param left  зашифрованный массив кодированных символов левой части блока
     * @param right зашифрованный массив кодированных символов правой части блока
     * @return массив расшифрованных символов из левой и правой части блока
     */
    private int[] decryptText(int[] left, int[] right) {
        for (int i = KEYS.length - 1; i > -1; i--) {
            int[] temp = right;
            right = left;
            left = temp;
            for (int j = 0; j < left.length; j++) {
                left[j] = temp[j] ^ secretFunc(KEYS[i], right[RESHUFFLE[j] - 1]);
            }
        }
        int[] decryptedPart = new int[BLOCK_SIZE];
        System.arraycopy(left, 0, decryptedPart, 0, BLOCK_SIZE/2);
        System.arraycopy(right, 0, decryptedPart, BLOCK_SIZE/2, BLOCK_SIZE/2);
        return decryptedPart;
    }
}