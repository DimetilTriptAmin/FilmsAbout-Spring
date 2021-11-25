package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.User;

import java.util.List;

public interface IUserRepository {
    List<String> getAllEmails();
    User getById(Long id);
    User getByUsername(String userName);
    User getByEmail(String email);
    Long registerUser(User user);
    Long updateAvatar(Long id, String avatar);
    Long changePassword(Long id, String newPassword);
}
