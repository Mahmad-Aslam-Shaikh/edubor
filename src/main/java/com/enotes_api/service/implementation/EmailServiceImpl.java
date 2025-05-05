package com.enotes_api.service.implementation;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.messages.EmailMessages;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
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
    public void sendRegistrationMail(UserEntity user, HttpServletRequest request) throws EmailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail, EmailMessages.REGISTRATION_EMAIL_TITLE);
            mimeMessageHelper.setSubject(EmailMessages.REGISTRATION_EMAIL_SUBJECT);
            mimeMessageHelper.setTo(user.getEmail());

            String baseUrl = getBaseUrl(request);

            String verificationLink =
                    baseUrl + RouteConstants.HOME + RouteConstants.USER_VERIFY + "?user-id=" + user.getId() +
                            "&code=" + user.getUserVerificationStatus().getVerificationCode();

            String content = String.format(EmailMessages.REGISTRATION_EMAIL_CONTENT, user.getFirstName(),
                    verificationLink);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | MailException | UnsupportedEncodingException e) {
            System.out.println(e.getClass());
            throw new EmailException(ExceptionMessages.UNABLE_TO_SEND_EMAIL_MESSAGE);
        }
    }

    public String getBaseUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        return url.replace(uri, "");
    }

}
