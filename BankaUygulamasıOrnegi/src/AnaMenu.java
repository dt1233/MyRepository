import java.util.Scanner;

public class AnaMenu {
    private KayitliMusterilerListesi musteriler;
    private KayitliMusteri musteri;
    private EmailSender sender;

    public AnaMenu(KayitliMusteri musteri,KayitliMusterilerListesi musteriler,EmailSender sender) {
        this.musteriler=musteriler;
        this.musteri = musteri;
        this.sender=sender;
    }

    public void baslat() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Ana Menü:");
            System.out.println("1. Hesap Bilgileri");
            System.out.println("2. Para Transferi");
            System.out.println("3. Kredi Başvurusu");
            System.out.println("4. Hesap Hareketleri");
            System.out.println("5. Çıkış");

            int secim = scanner.nextInt();
            scanner.nextLine(); // Satır sonunu temizle

            switch (secim) {
                case 1:
                    hesapBilgileri();
                    break;
                case 2:
                    paraTransferi(scanner);
                    break;
                case 3:
                    krediBasvurusu();
                    break;
                case 4:
                    hesapHareketleriniGoruntule();
                    break;
                case 5:
                    System.out.println("Çıkış yapılıyor.");
                    return;
                default:
                    System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }
    }

    private void hesapBilgileri() {
        System.out.println("Hesap Bilgileri:");
        System.out.println("İsim: " + musteri.getIsim());
        System.out.println("Soyisim: " + musteri.getSoyisim());
        System.out.println("Hesap No: " + musteri.getHesapNo());
        System.out.println("Bakiye: " + musteri.getToplamBakiye());
        System.out.println("Kredi Notu: " + musteri.getKrediNotu());
    }

    private void paraTransferi(Scanner scanner) {
        System.out.print("Transfer yapılacak hesap numarası: ");
        String hesapNo = scanner.nextLine();
        for(KayitliMusteri m : musteriler.getMusteriler()) {

            if(m.getHesapNo().equals(hesapNo)) {
                System.out.print(m.getIsim()+" "+m.getSoyisim()+" hesabına transfer miktarı: ");
                double miktar = scanner.nextDouble();

                if(miktar<=0) {

                    System.out.println("Hata: Lütfen geçerli bir para tutarı giriniz.");
                }
                else {

                    if (miktar > musteri.getToplamBakiye()) {
                        System.out.println("Yetersiz bakiye.");
                    } else {
                        musteri.setToplamBakiye(musteri.getToplamBakiye() - miktar);
                        m.setToplamBakiye(m.getToplamBakiye()+miktar);
                        sender.sendEmail(musteri.getEmail(),"DEKONT",(m.getHesapNo()+" hesabına "+miktar+" TL gönderilmiştir."));
                        sender.sendEmail(m.getEmail(),"DEKONT",(musteri.getIsim()+" "+musteri.getSoyisim()+" tarafından hesabınıza "+miktar+" TL gönderilmiştir."));
                        HesapHareketleri.logEkle("Hesap No " + hesapNo + " hesabına " + miktar + " TL transfer edildi.");
                        System.out.println("Transfer başarılı!");
                        return;
                    }
                }
           }
        }
        System.out.println("Hata: Hesap numarası hatalı!");
    }
    private void krediBasvurusu() {
        Kredi kredi=KrediFabrikasi.krediOlustur(musteri);
        int limit = kredi.krediLimitHesapla(musteri);

        System.out.println(kredi.getFaiz());
        if(limit!=0) {
            System.out.println("Kredi başvurunuz onaylandı! En yakın şubemize başvurarak işlemleri başlatabilirsiniz.\nTutar: "+limit+"\nÖdenecek Tutar: "+(limit+(limit*(kredi.getFaiz()/100))));
        }
        else {
            System.out.println("Kredi başvurunuz onaylanmadı!");
        }
    }

    private void hesapHareketleriniGoruntule() {
        System.out.println("HesapHareketleri dosyasını kontrol edin.");
    }
}