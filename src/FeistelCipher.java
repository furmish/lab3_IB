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
    public static final int[] RESHUFFLE = new int[]{3, 1, 2, 4};
    public static final int BLOCK_SIZE = RESHUFFLE.length * 2;
    public static final int[] KEYS = new int[]{2201, 1054, 3245};
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
