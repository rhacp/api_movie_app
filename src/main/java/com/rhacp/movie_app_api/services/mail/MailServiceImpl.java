package com.rhacp.movie_app_api.services.mail;

import com.rhacp.movie_app_api.models.entities.Mail;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.utils.properties.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final Properties properties;

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(Properties properties, JavaMailSender javaMailSender) {
        this.properties = properties;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendMail(String target, Mail mail, String methodName) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(properties.getSenderAddress());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setTo(target);

        javaMailSender.send(simpleMailMessage);
        log.info("Email sent. Method: {}.", methodName);
    }

    @Override
    public Mail prepareMailCreateUser(String email, String password, String methodName) {
        Mail mail = new Mail();
        String message = "Your Movies Hub account was created successfully!\n" +
                "You can find your credentials below:\n\n" +
                "Username: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "Hope you have a good day!";

        mail.setSubject("Your Movies Hub account details!");
        mail.setMessage(message);
        log.info("Email created. Method: {}.", methodName);

        return mail;
    }
}
