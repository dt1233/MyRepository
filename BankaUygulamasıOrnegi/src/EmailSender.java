import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private final String username;
    private final String password;

    public EmailSender() {
        this.username = "Mail adresin";
        this.password = "Mail adresi için uygulama şifresi";
    }

    public void sendEmail(String toEmail, String subject, String messageContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}