package com.enotes_api.service.implementation;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.messages.EmailMessages;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationMail(UserEntity user) throws EmailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail, EmailMessages.REGISTRATION_EMAIL_TITLE);
            mimeMessageHelper.setSubject(EmailMessages.REGISTRATION_EMAIL_SUBJECT);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setText(EmailMessages.REGISTRATION_EMAIL_CONTENT);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | MailException | UnsupportedEncodingException e) {
            System.out.println(e.getClass());
            throw new EmailException(ExceptionMessages.UNABLE_TO_SEND_EMAIL);
        }
    }

}
