import java.util.Random;

public class GenerateHesapNo {

    private KayitliMusterilerListesi musteriler;
    private String hesapNo;

    public String generate(KayitliMusterilerListesi musteriler) {
        this.musteriler = musteriler;
        Random random = new Random();
        this.hesapNo = "TR" + String.format("%08d", random.nextInt(100000000));

        if (!musteriler.getMusteriler().isEmpty()) {
            for (KayitliMusteri musteri : musteriler.getMusteriler()) {
                if (musteri.getHesapNo().equals(hesapNo)) {
                    this.hesapNo = "TR" + String.format("%08d", random.nextInt(100000000));
                }
            }
        }

        return this.hesapNo;
    }
}