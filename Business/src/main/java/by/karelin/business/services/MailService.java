package by.karelin.business.services;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IMailService;
import by.karelin.business.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailService implements IMailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMail(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAll(List<String> emails, String subject, String message) {
        for (String email:
             emails) {
            sendMail(email, subject, message);
        }
    }
}
