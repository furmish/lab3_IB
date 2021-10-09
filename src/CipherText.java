import java.util.Arrays;

/**
 * Класс зашифрованного текста с возможнностью дешифрования
 */
public class CipherText implements FeistelCipher {
    private final String cipherText;

    public CipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    /**
     *Дешифрует каждый блок из {@link FeistelCipher#RESHUFFLE} * 2 символов в строке по порядку.
     *@return зашифрованную строку
     */
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

    /**
     *Функция дешифрования по алгоритму Фейстеля
     * @param left зашифрованный массив кодированных символов левой части блока
     * @param right зашифрованный массив кодированных символов правой части блока
     * @return массив расшифрованных символов из левой и правой части блока
     */
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
}