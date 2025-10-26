public class KayitliMusteri extends YeniMusteri {
    private String hesapNo;
    private double toplamBakiye;
    private double krediNotu;

    public void setHesapNo(KayitliMusterilerListesi musteriler) {
        this.hesapNo=new GenerateHesapNo().generate(musteriler);
    }

    public String getHesapNo() {

        return hesapNo;
    }

    public double getToplamBakiye() {
        return toplamBakiye;
    }

    public void setToplamBakiye(double toplamBakiye) {
        this.toplamBakiye = toplamBakiye;
    }

    public void setKrediNotu(KayitliMusteri musteri) {

        this.krediNotu = new KrediNotuHesapla().krediNotu(musteri);
    }

    public double getKrediNotu() {
        return krediNotu;
    }
}
