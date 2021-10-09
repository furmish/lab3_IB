import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите строку для шифрования:");
        Scanner sc = new Scanner(System.in);
        PlainText plainText = new PlainText(sc.nextLine());
        CipherText cipherText = new CipherText(plainText.encryptText());
        System.out.println("Ваша зашифрованная строка:\n" + plainText.encryptText());
        System.out.println("Расшифрованная строка:");
        System.out.println(cipherText.decryptText());
    }
}
