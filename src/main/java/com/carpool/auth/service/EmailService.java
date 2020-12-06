package com.carpool.auth.service;

import com.carpool.auth.model.Mail;

import javax.mail.MessagingException;

public interface EmailService {

    void sendSimpleEmail(Mail mail);

    void sendComplexEmail(Mail mail);
}
