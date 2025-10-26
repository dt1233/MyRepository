public class CiftciKredisi extends GenelKredi implements MeslegeOzelKredi {

    @Override
    public double meslekKredisi() {
        return (Kredi.faiz/ 10) + 4.02;
    }

    @Override
    public int krediLimitHesapla(KayitliMusteri musteri) {
        return super.krediLimitHesapla(musteri);
    }

    @Override
    public double getFaiz() {

        return meslekKredisi();
    }
}
