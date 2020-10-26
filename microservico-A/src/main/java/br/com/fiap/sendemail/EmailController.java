package br.com.fiap.sendemail;

import br.com.fiap.sendemail.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import static br.com.fiap.sendemail.utils.EmailBody.emailBody;
import static br.com.fiap.sendemail.utils.GoogleApiUrl.googleApiUrl;
import static br.com.fiap.sendemail.utils.SaveMapsImage.saveImage;

@RestController
public class EmailController {
    @Autowired
    private SmtpCredentials smtpCredentials;
    @Autowired
    private SmtpConfig smtpConfig;
    @Autowired
    private GoogleMaps googleMaps;
    @Autowired
    private RabbitMq rabbitMq;

    @PostMapping("/email")
    public ResponseEntity createEmail(@RequestBody EmailContent newEmail) throws IOException, MessagingException {
        try {
            String googleUrl = googleApiUrl(newEmail.getLatitude(), newEmail.getLongitude(), googleMaps.getGooglemaps());
            System.out.println(googleUrl);
            saveImage(googleUrl, "/var/tmp/test.png");
            sendmail(newEmail);
            return new ResponseEntity("Email sent with success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Sorry, the email wasn`t sent.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private void sendmail(EmailContent newEmail) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpConfig.getAuth());
        props.put("mail.smtp.starttls.enable", smtpConfig.getStarttlsEnable());
        props.put("mail.smtp.host", smtpConfig.getHost());
        props.put("mail.smtp.port", smtpConfig.getPort());

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpCredentials.getEmail(), smtpCredentials.getPassword());
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(smtpCredentials.getEmail(), false));


        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(newEmail.getEmail()));
        msg.setSubject("System Report Alert - Drone ID: " + newEmail.getDroneID());
        msg.setContent("Drone Information...", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        String body = emailBody(newEmail);
        messageBodyPart.setContent(body, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/var/tmp/test.png");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}