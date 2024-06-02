package com.example.mailingservice.service.registrationObserver;

import com.example.mailingservice.model.RegistrationAttempt;
import com.example.mailingservice.service.ActivationLinkConstructor;
import com.example.mailingservice.service.gmail.MessageCreator;
import com.example.mailingservice.service.gmail.MimeMessageCreator;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailingRegistrationAttemptObserverImpl implements RegistrationAttemptObserver {
    private final TemplateEngine templateEngine;
    private final ActivationLinkConstructor activationLinkConstructor;
    private final Gmail gmail;

    public MailingRegistrationAttemptObserverImpl(TemplateEngine templateEngine, ActivationLinkConstructor activationLinkConstructor, Gmail gmail) {
        this.templateEngine = templateEngine;
        this.activationLinkConstructor = activationLinkConstructor;
        this.gmail = gmail;
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
            Message message = MessageCreator.createMessageWithEmail(mimeMessage);

            gmail.users().messages().send("me", message).execute();
        } catch (Exception e){
            System.out.println(e);
        }


    }
}
