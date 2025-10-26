import java.util.ArrayList;
import java.util.List;

public class KayitliMusterilerListesi {
    private List<KayitliMusteri> musteriler;

    public KayitliMusterilerListesi() {
        musteriler = new ArrayList<>();
    }

    public void musteriEkle(KayitliMusteri musteri) {
        musteriler.add(musteri);
    }

    public List<KayitliMusteri> getMusteriler() {
        return musteriler;
    }
}
