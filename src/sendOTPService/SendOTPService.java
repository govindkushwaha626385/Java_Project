package sendOTPService;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendOTPService {
    public static void sendOTP(String email, String genOTP, String messaage1) {
        // Recipient's email ID needs to be mentioned.
        String to = email;
        // Sender's email ID needs to be mentioned
        String from = "govindkushwahabusiness@gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        // properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "zqsiwvapcjfqdqww");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Bank Details");
            // Now set the actual message
            message.setText(messaage1 + " " + genOTP + " Thank You");
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");
            System.out.println("Please Check Your Email...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

