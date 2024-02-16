package com.imesh.lab.utils.mail;

import com.imesh.lab.utils.data_mapper.DataMapper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static EmailSender emailSender;

    public static synchronized EmailSender getEmailSender() {
        if (emailSender == null) {
            emailSender = new EmailSender();
        }
        return emailSender;
    }

    public void sendMail(String recipientEmail, String subject, String body)  {
        final String senderEmail = "abclabtests@gmail.com";
        final String password = "jlmt nxay bgln bblr";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
