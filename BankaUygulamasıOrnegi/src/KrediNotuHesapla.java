public class KrediNotuHesapla {

    private double krediNotu;

    public double krediNotu(KayitliMusteri musteri) {

        krediNotu = musteri.getOrtalamaGelir()*(5.88/100000);
        return krediNotu;
    }
}
