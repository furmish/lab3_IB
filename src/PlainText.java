import java.util.Arrays;

/**
 * Класс открытого текста с возможностью шифрования
 */
public class PlainText implements FeistelCipher {
    private String plainText;

    /**Конструктор - создание нового объекта
     * */
    public PlainText(String plainText) {
        this.plainText = plainText;
    }
/**Шифрует каждый блок из {@link FeistelCipher#RESHUFFLE} * 2 символов в строке по порядку.
 * Если длина открытого текста не делится на блоки одинакоговой длинны,
 * то выполняется {@link #addSpaces()}
 * @return зашифрованную строку*/
    public String encryptText() {
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

    /** Функция шифрования по алгоритму Фейстеля
     * @param left массив кодированных символов левой части блока
     * @param right массив кодированных символов правой части блока
     * @return массив зашифрованных символов из левой и правой части блока
     */
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


    /**
     * Метод, который добавляет недостающее кол-во символов, для корректной работы алгоритма, при дешифровке эти символы удалятся
     * <p>
     *Пример:<p>
     *<blockquote><pre>
     *"это не понадобится".length() == 18
     * после выполнения метода будет выглядеть так:
     *"это не понадобится      ".length() == 24
     * </pre><blockquote>
     */
    private void addSpaces () {
        int startPlainTextLength = plainText.length();
        int neededPlainTextLength = RESHUFFLE.length * 2 * (startPlainTextLength / (RESHUFFLE.length * 2) + 1);
        for (int i = plainText.length();  i <  neededPlainTextLength; i++) {
            plainText += " ";
        }
    }
}