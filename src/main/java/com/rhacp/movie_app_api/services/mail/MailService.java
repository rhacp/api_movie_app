package com.rhacp.movie_app_api.services.mail;

import com.rhacp.movie_app_api.models.entities.Mail;

public interface MailService {

    void sendMail(String target, Mail mail, String methodName);

    Mail prepareMailCreateUser(String email, String password, String methodName);
}
