package by.karelin.domain.models;

import org.hibernate.annotations.Type;
import org.hibernate.dialect.OracleTypesHelper;

import javax.persistence.*;

@Entity
@Table(name = "FA_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwordHash;
    @Lob
    private String avatar;
    private String email;

    public User() {}
    public User(String name, String passwordHash, String avatar, String email){
        this.name = name;
        this.passwordHash = passwordHash;
        this.avatar = avatar;
        this.email = email;
    }

    //region getters&setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion
}
