package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.User;

public interface IUserRepository {
    User getById(Long id);
    User getByUsername(String userName);
    User getByEmail(String email);
    boolean tryRegisterUser(User user);
    boolean tryUpdateAvatar(Long id, String avatar);
}
