package com.SWOOSH.service;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    // Не работает, настроить.
    public void sendEmailWithPasswordForEmployee(String email, String passwordForEmployee) {
        String user = "swoosh.carwash@yandex.ru";
        String password = "Swooshmail1@1342asd";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] addresses = {new InternetAddress(email)};
            msg.setRecipients(RecipientType.TO, addresses);
            msg.setSubject("Swoosh confirm");
            msg.setSentDate(new Date());
            msg.setText("Ваш пароль: " + passwordForEmployee);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String email, String message) {
        String user = "swoosh.carwash@yandex.ru";
        String password = "Swooshmail1@1342asd";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] addresses = {new InternetAddress(email)};
            msg.setRecipients(RecipientType.TO, addresses);
            msg.setSubject("Swoosh confirm");
            msg.setSentDate(new Date());
            msg.setText("Код подтверждения: " + message);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

