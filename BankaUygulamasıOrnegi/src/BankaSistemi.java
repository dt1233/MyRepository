public class BankaSistemi {
    public static void main(String[] args) {
        KayitliMusterilerListesi musteriler = new KayitliMusterilerListesi();
        EmailSender sender = new EmailSender();
        GirisMenusu girisMenusu = new GirisMenusu(musteriler,sender);
        girisMenusu.baslat();
    }
}