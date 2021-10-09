/**
 * Интерфейс шифра Фейстеля.<p>
 * Содержит константы:<p>
 * <blockquote><pre>
 * {@link #KEYS} - массив ключей
 * {@link #RESHUFFLE} - массив перестановок
 * {@link #ALPHABET_SIZE} - размер алфавита </blockquote></pre><p>
 * И общую функцию для шифрования и дешифрования {@link #secretFunc(int, int)}
 */
public interface FeistelCipher {
    int[] RESHUFFLE = new int[]{4,1,3,2};
    int[] KEYS = new int[]{354453, 256453, 191146};
    int ALPHABET_SIZE = 65536;
    /**
     * Функция, использующаяся для шифрования
     * @param key ключ
     * @param value значение
     * @return измененное значение
     */
    default int secretFunc(int key, int value) {
        return (key * value) % ALPHABET_SIZE;
    }
}
