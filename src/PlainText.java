import java.util.Arrays;

public class PlainText implements FeistelCipher {
    private String plainText;


    public PlainText(String plainText) {
        this.plainText = plainText;
    }
/*@encryptText() шифрует каждый блок из n символов в строке по порядку, где n - количество перестановок * 2 */
    public String encryptText() {
        /*проверяем длину открытого текста. Если она не делится на блоки одинакоговой длинны,
        * то добавляем пробелы до того момента, пока не выполнится условие*/
        if (plainText.length() % (RESHUFFLE.length * 2) != 0){
            addSpaces();
        }
        StringBuilder sb = new StringBuilder();
        int beginIndex = 0;
        /*цикл для получение подстрок, которые делятся на левую и правую части,
        передаем left и right в метод encryptText(left, right), который вернет нам объедененный массив из зашифрованных left и right*/
        for (int i = RESHUFFLE.length * 2 - 1; i < plainText.length(); i += RESHUFFLE.length * 2) {
            int[] left = plainText.substring(beginIndex, beginIndex + RESHUFFLE.length).codePoints().toArray();
            int[] right = plainText.substring(beginIndex + RESHUFFLE.length, i + 1).codePoints().toArray();
                Arrays.stream(encryptText(left, right)).forEach(value -> sb.append((char) value));
                beginIndex = i + 1;
        }
        return sb.toString();
    }
/*@encryptText(int[], int[]) получает две части строки в кодовом эквиваленте, и по алгоритму Фейстеля шифрует их,
* затем зашифрованные массивы left и right помещаются в массив encryptedPart, который и возвращает этот метод. */
    private int[] encryptText(int[] left, int[] right) {
        for (int key : KEYS) {
            int[] temp = left;
            left = right;
            right = new int[]{0, 0, 0, 0};
            for (int j = 0; j < left.length; j++) {
                right[j] = temp[j] ^ secretFunc(key, left[RESHUFFLE[j] - 1]);
            }
        }
        int[] encryptedPart = new int[left.length * 2];
        System.arraycopy(left, 0, encryptedPart, 0, 4);
        System.arraycopy(right, 0, encryptedPart, 4, 4);
        return encryptedPart;
    }

    /*функция для шифрования*/
    private int secretFunc(int key, int value) {
        return (key * value) % ALPHABET_SIZE;
    }
    /*метод, который добавляет недостающее кол-во символов, для корректной работы
    * алгоритма, при дешифровке эти символы удалятся*/
    private void addSpaces () {
        int startPlainTextLength = plainText.length();
        int neededPlainTextLength = RESHUFFLE.length * 2 * (startPlainTextLength / (RESHUFFLE.length * 2) + 1);
        for (int i = plainText.length();  i <  neededPlainTextLength; i++) {
            plainText += " ";
        }
    }
}

/* public String encryptText() {
        int[] left = encryptText(plainText.substring(0, 4).codePoints().toArray(), plainText.substring(4, 8).codePoints().toArray());
        int[] right = encryptText(plainText.substring(8, 12).codePoints().toArray(), plainText.substring(12).codePoints().toArray());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(left).forEach(value -> sb.append((char) value));
        Arrays.stream(right).forEach(value -> sb.append((char) value));
        return sb.toString();
    }*/