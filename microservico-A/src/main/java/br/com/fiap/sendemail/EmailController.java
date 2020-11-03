package br.com.fiap.sendemail;

import br.com.fiap.sendemail.entity.*;
import br.com.fiap.sendemail.utils.RabbitConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.*;

import static br.com.fiap.sendemail.utils.EmailBody.emailBody;
import static br.com.fiap.sendemail.utils.EmailDestination.emailDestination;
import static br.com.fiap.sendemail.utils.GoogleApiUrl.googleApiUrl;
import static br.com.fiap.sendemail.utils.SaveMapsImage.saveImage;

/*@RestController*/
@Service
@EnableScheduling
public class EmailController {
    @Autowired
    private SmtpCredentials smtpCredentials;
    @Autowired
    private SmtpConfig smtpConfig;
    @Autowired
    public GoogleMaps googleMaps;
    @Autowired
    private static RabbitMq rabbitMq;

    @Scheduled(fixedDelay = 60)
    public void rabbitMqService() {
        RabbitTemplate template = new RabbitTemplate(RabbitConfiguration.getConnection());
        ArrayList<EmailContent> droneData = new ArrayList();
        int i;
        for (i = 0; i < 6; i++ ) {

                try {

                    byte[] body = template.receive("drone.allinfo").getBody();

                    EmailContent emailContent = new ObjectMapper().readValue(body, EmailContent.class);
                    droneData.add(emailContent);

                    for (EmailContent values : droneData) System.out.println(values);
                    System.out.println("fim");
                    Thread.sleep(10000);

                    if (i == 5) {
                        EmailContent maxTemp = Collections.max(droneData, Comparator.comparing(s -> Integer.parseInt(s.getTemperatura())));
                        System.out.println(maxTemp.getTemperatura());
                        EmailContent minTemp = Collections.min(droneData, Comparator.comparing(s -> Integer.parseInt(s.getTemperatura())));
                        System.out.println(minTemp.getTemperatura());
                        EmailContent minUmidade = Collections.min(droneData, Comparator.comparing(s -> Integer.parseInt(s.getUmidade())));
                        System.out.println(minUmidade.getUmidade());
                        if (Integer.parseInt(String.valueOf(maxTemp.getTemperatura())) >= 35 )  {
                            System.out.println("Temperatua Alta Registrada");
                            String googleUrl = googleApiUrl(maxTemp.getLatitude(), maxTemp.getLongitude(), googleMaps.getGooglemaps());
                            saveImage(googleUrl, smtpConfig.getAttachPath());
                            sendmail(maxTemp, "Temperatua Alta Registrada");
                            System.out.println("Email enviado");
                        } else if (Integer.parseInt(String.valueOf(minTemp.getTemperatura())) <= 0) {
                            System.out.println("Temperatura Baixa Registrada");
                            String googleUrl = googleApiUrl(minTemp.getLatitude(), minTemp.getLongitude(), googleMaps.getGooglemaps());
                            saveImage(googleUrl, smtpConfig.getAttachPath());
                            sendmail(minTemp, "Temperatura Baixa Registrada");
                            System.out.println("Email enviado");

                        }else if (Integer.parseInt(String.valueOf(minUmidade.getUmidade())) <= 15) {
                            System.out.println("Umidade Baixa Registrada");
                            String googleUrl = googleApiUrl(minUmidade.getLatitude(), minUmidade.getLongitude(), googleMaps.getGooglemaps());
                            saveImage(googleUrl, smtpConfig.getAttachPath());
                            sendmail(minUmidade, "Umidade Baixa Registrada");
                            System.out.println("Email enviado");
                        } else {
                            System.out.println("Sistema Normal");
                        }
                    } else {}


                } catch (NullPointerException | IOException | InterruptedException | MessagingException ex) {
                    System.out.println("Fila vazia");

                }
            }


    }

    private void sendmail(EmailContent newEmail, String issue) throws AddressException, MessagingException, IOException {
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

        String emailTo = emailDestination(newEmail.getEmail(),smtpConfig.getEmailDestination());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
        msg.setSubject("System Report Alert - Drone ID: " + newEmail.getDrone_id());
        msg.setContent("Drone Information...", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        String body = emailBody(newEmail, issue);
        messageBodyPart.setContent(body, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(smtpConfig.getAttachPath());
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}