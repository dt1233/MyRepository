import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HesapHareketleri {
    private static final String LOG_FILE = "C:\\Users\\VİCTUS\\IdeaProjects\\BankaDeneme\\HesapHareketleri\\hesap_hareketleri.txt";

    public static void logEkle(String mesaj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(mesaj);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Hesap hareketleri loglanırken hata oluştu: " + e.getMessage());
        }
    }
}