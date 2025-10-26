import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class GirisMenusu {
    private KayitliMusterilerListesi musteriler;
    private EmailSender sender;

    public GirisMenusu(KayitliMusterilerListesi musteriler,EmailSender sender) {
        this.musteriler = musteriler;
        this.sender = sender;
    }

    public void baslat() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hoşgeldiniz! Lütfen bir seçenek seçin:");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol");
            System.out.println("3. Şifremi Unuttum");
            System.out.println("4. Çıkış");

            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1:
                    girisYap(scanner);
                    break;
                case 2:
                    kayitOl(scanner);
                    break;
                case 3:
                    sifremiUnuttum(scanner);
                    break;
                case 4:
                    System.out.println("Çıkış yapılıyor. İyi günler!");
                    return;
                default:
                    System.out.println("Hata: Lütfen geçerli bir işlem numarası giriniz!");
            }
        }
    }

    private void girisYap(Scanner scanner) {
        System.out.print("TCKN veya E-mail: ");
        String id = scanner.nextLine();

        for (KayitliMusteri musteri : musteriler.getMusteriler()) {
            if (musteri.getTckn().equals(id) || musteri.getEmail().equals(id) ) {

                System.out.print("Şifre: ");
                String sifre = scanner.nextLine();

                if (musteri.getSifre().equals(sifre)) {

                    Random random = new Random();
                    String check=String.format("%04d", random.nextInt(10000));
                    sender.sendEmail(musteri.getEmail(),"GİRİŞ KODU", check);
                    System.out.print("Doğrulama kodu: ");
                    String kod=scanner.nextLine();

                    if(check.equals(kod)) {
                        HesapHareketleri.logEkle(LocalDateTime.now()+" tarihinde başarılı giriş!");
                        System.out.println("Giriş başarılı! Hoşgeldiniz, " + musteri.getIsim() + "!");
                        AnaMenu anaMenu = new AnaMenu(musteri,musteriler,sender);
                        anaMenu.baslat();
                        return;
                    }else {
                        System.out.println("Hata: Kod hatalı!");
                        return;
                    }

                } else {
                    HesapHareketleri.logEkle(LocalDateTime.now()+" tarihinde başarısız giriş denemesi.");
                    System.out.println("Hata: Şifre hatalı!");
                    return;
                }
            }
        }

        System.out.println("Hata: TCKN veya E-mail hatalı!");
    }

    private void kayitOl(Scanner scanner) {
        KayitliMusteri yeniMusteri = new KayitliMusteri();

        System.out.print("İsim: ");
        yeniMusteri.setIsim(scanner.nextLine());
        System.out.print("Soyisim: ");
        yeniMusteri.setSoyisim(scanner.nextLine());
        System.out.print("TCKN: ");
        yeniMusteri.setTckn(scanner.nextLine());
        System.out.print("Meslek: ");
        yeniMusteri.setMeslek(scanner.nextLine());
        System.out.print("Ortalama Gelir: ");
        yeniMusteri.setOrtalamaGelir(scanner.nextDouble());
        scanner.nextLine();
        System.out.print("E-mail: ");
        yeniMusteri.setEmail(scanner.nextLine());

        Random random = new Random();
        String check=String.format("%04d", random.nextInt(10000));
        sender.sendEmail(yeniMusteri.getEmail(),"KAYIT OL", check);
        System.out.print("Doğrulama kodu: ");
        String kod=scanner.nextLine();

        if(check.equals(kod)) {

            System.out.println("Doğrulama başarılı!");
            System.out.print("Şifre oluşturunuz: ");
            yeniMusteri.setSifre(scanner.nextLine());
            yeniMusteri.setHesapNo(musteriler);
            yeniMusteri.setKrediNotu(yeniMusteri);
            musteriler.musteriEkle(yeniMusteri);
            musteriler.getMusteriler().get(0).setToplamBakiye(100);

            System.out.println("Kayıt başarılı! Giriş yapabilirsiniz.");
        } else {

            System.out.println("Hata: Kod hatalı!");
        }
    }

    private void sifremiUnuttum(Scanner scanner) {
        System.out.print("TCKN: ");
        String tckn = scanner.nextLine();

        for (KayitliMusteri musteri : musteriler.getMusteriler()) {
            if (musteri.getTckn().equals(tckn)) {

                System.out.print("E-mail: ");
                String email = scanner.nextLine();

                if(musteri.getEmail().equals(email)) {

                    Random random = new Random();
                    String check=String.format("%04d", random.nextInt(10000));
                    sender.sendEmail(musteri.getEmail(),"ŞİFREMİ UNUTTUM", check);
                    System.out.print("Doğrulama kodu: ");
                    String kod=scanner.nextLine();

                    if(check.equals(kod)) {

                        System.out.println("Doğrulama başarılı!");
                        System.out.print("Yeni şifre: ");
                        String yeniSifre=scanner.nextLine();
                        musteri.setSifre(yeniSifre);
                        System.out.println("Şifre başarıyla güncellendi!");
                        return;
                    } else {

                        System.out.println("Hata: Kod hatalı!");
                        return;
                    }

                } else {

                    System.out.println("Hata: Email hatalı!");
                    return;
                }
            }
        }
        System.out.println("Hata: Girmiş olduğunuz TCKN ait bir hesap bulunamadı!");
    }
}