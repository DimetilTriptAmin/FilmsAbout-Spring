package by.karelin.business.services.interfaces;

import java.util.List;

public interface IMailService {
    void sendMail(String toAddress, String subject, String message);
    void sendAll(List<String> emails, String subject, String message);
}
