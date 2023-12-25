package project.intro2se.ticketify.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromUser;
    public static final String CONFIRM_EMAIL_MSG = "Your new account has been created. Please click a link below to verify your account\n";
    @Async
    public void sendSimpleEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromUser);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("Email sent");
    }
    @Async
    public void sendEmailWithEmbeddedImages(String name, String toEmail, String subject, String body, byte[] qrCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setPriority(1);
        helper.setFrom(fromUser);
        helper.setSubject(subject);
        helper.setTo(toEmail);
        helper.setText(body);
        ByteArrayDataSource image = new ByteArrayDataSource(qrCode, "image/jpeg");
        helper.addAttachment("tickets", image);
        mailSender.send(message);
        log.info("Email sent");
    }
    public static String contentOfResetPassword(String link){
        return  "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password (the link will be expired in 30 min):</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
    }

}
