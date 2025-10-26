    public class GenelKredi implements Kredi {

        @Override
        public double getFaiz() {
            return Kredi.faiz;
        }

        @Override
        public int krediLimitHesapla(KayitliMusteri musteri) {

            int limit= (int) (musteri.getOrtalamaGelir()+(musteri.getKrediNotu()*5000));
            return limit;
        }
}
