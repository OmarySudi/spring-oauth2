package com.carpool.auth.service;

import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImplementation implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendSimpleEmail(Mail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("kekovasudi@gmail.com");
        msg.setTo(mail.getMailTo());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getText());

        try{
            mailSender.send(msg);
        }catch(Exception ex)
        {
            throw new InternalServerErrorException("Could not be registered,There is an internal server error in sending emails");
        }

    }

    @Override
    public void sendComplexEmail(Mail mail){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper  helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

//            Context context = new Context();
//            context.setVariables(mail.getProps());
//
//            String html = templateEngine.process("newsletter-template", context);
//            helper.setTo(mail.getMailTo());
//            helper.setText(html, true);
//            helper.setSubject(mail.getSubject());
//            helper.setFrom(mail.getFrom());
//            emailSender.send(message);
            Context context = new Context();

            //if(mail.getProps().size() > 0)
            context.setVariables(mail.getProps());

            String html = templateEngine.process("confirm_registration",context);

            helper.setTo(mail.getMailTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            mailSender.send(message);

        }catch(MessagingException ex){
            throw new InternalServerErrorException("Could not be registered,There is an internal server error in sending emails");
        }
    }
}
