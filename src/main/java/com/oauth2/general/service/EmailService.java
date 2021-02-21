package com.oauth2.general.service;

import com.oauth2.general.model.Mail;

import javax.mail.SendFailedException;

public interface EmailService {

    void sendSimpleEmail(Mail mail);

    void sendComplexEmail(Mail mail) throws SendFailedException;
}
