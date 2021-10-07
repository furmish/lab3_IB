import java.util.Arrays;
import java.util.stream.Stream;

public class CipherText implements FeistelCipher {
    private String cipherText;

    public CipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    /*@decryptText() дешифрует каждый блок из n символов в строке по порядку, где n - кол-во перестановок * 2 */
    public String decryptText() {
        StringBuilder sb = new StringBuilder();
        int beginIndex = 0;
        /*цикл для получение подстрок, которые делятся на левую и правую части,
        передаем left и right в метод @decryptText(left, right), который вернет нам объедененный массив из расшифрованных left и right*/
        for (int endIndex = RESHUFFLE.length * 2 - 1; endIndex < cipherText.length(); endIndex += RESHUFFLE.length * 2) {
            int[] left = cipherText.substring(beginIndex, beginIndex + RESHUFFLE.length).chars().toArray();
            int[] right = cipherText.substring(beginIndex + RESHUFFLE.length, endIndex + 1).chars().toArray();
            Arrays.stream(decryptText(left, right)).forEach(value -> sb.append((char) value));
            beginIndex = endIndex + 1;
        }
        return sb.toString().trim();
    }

    /*@decryptText(int[], int[]) получает две части зашифрованной строки в кодовом эквиваленте, и инверсированно
    с шафрованием дешифрует их затем расшифрованные массивы left и right объеденяются в один массив и возвращаются */
    private int[] decryptText(int[] left, int[] right) {
        for (int i = KEYS.length; i > 0; i--) {
            int[] temp = right;
            right = left;
            left = temp;
            for (int j = 0; j < left.length; j++) {
                left[j] = temp[j] ^ secretFunc(KEYS[i - 1], right[RESHUFFLE[j] - 1]);
            }
        }
        return Stream.concat(Arrays.stream(left).boxed(), Arrays.stream(right).boxed())
                .mapToInt(i -> i).toArray();
    }
}