package com.example.mailingservice.service.registrationObserver;

import com.example.mailingservice.model.RegistrationAttempt;
import com.example.mailingservice.service.ActivationLinkConstructor;
import com.example.mailingservice.service.gmail.MimeMessageCreator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
public class MailingRegistrationAttemptObserverImpl implements RegistrationAttemptObserver {
    private final TemplateEngine templateEngine;
    private final ActivationLinkConstructor activationLinkConstructor;
    private final JavaMailSender emailSender;

    public MailingRegistrationAttemptObserverImpl(TemplateEngine templateEngine, ActivationLinkConstructor activationLinkConstructor, JavaMailSender emailSender) {
        this.templateEngine = templateEngine;
        this.activationLinkConstructor = activationLinkConstructor;
        this.emailSender = emailSender;
    }

    @Override
    public void update(RegistrationAttempt registrationAttempt) {
        Context context = new Context();
        context.setVariable("nickname", registrationAttempt.nickname());
        context.setVariable("activationLink", activationLinkConstructor.constructActivationLink(registrationAttempt.id()));
        String messageBody = templateEngine.process("RegistrationConfirmationEmail.html", context);
        try{
            MimeMessage mimeMessage = MimeMessageCreator.createEmail(
                    registrationAttempt.email(),
                    "knowyourteacher.org@gmail.com",
                    "KnowYourTeacher profile activation",
                    messageBody
            );
            emailSender.send(mimeMessage);
            System.out.println("successfully sent email to " + registrationAttempt.email());
        } catch (Exception e){
            System.out.println(e);
        }


    }
}
