import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlainText plainText = new PlainText(sc.nextLine());
        System.out.println(plainText.encryptText());
        CipherText cipherText = new CipherText(plainText.encryptText());
        System.out.println(cipherText.decryptText());
    }
}
