/**
 * Абстрактный класс шифра Фейстеля.<p>
 * Содержит константы:<p>
 * <blockquote><pre>
 * {@link #KEYS} - массив ключей
 * {@link #RESHUFFLE} - массив перестановок
 * {@link #ALPHABET_SIZE} - размер алфавита </blockquote></pre><p>
 * И общую функцию для шифрования и дешифрования {@link #secretFunc(int, int)}
 */
public abstract class FeistelCipher {
    public static final int[] RESHUFFLE = new int[]{2, 1, 4, 3};
    public static final int[] KEYS = new int[]{456, 123, 1053};
    public static final int ALPHABET_SIZE = 65536;

    /**
     * Функция, использующаяся для шифрования
     *
     * @param key   ключ
     * @param value значение
     * @return измененное значение
     */
    public int secretFunc(int key, int value) {
        return (key * value) % ALPHABET_SIZE;
    }
}
