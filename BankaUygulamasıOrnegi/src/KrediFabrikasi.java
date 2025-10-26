public class KrediFabrikasi {
    public static Kredi krediOlustur(KayitliMusteri musteri) {
        switch (musteri.getMeslek().toLowerCase()) {
            case "çiftçi":
                return new CiftciKredisi();
            case "öğretmen":
                return new OgretmenKredisi();
            case "emekli":
                return new EmekliKredisi();
            default:
                return new GenelKredi();
        }
    }
}
