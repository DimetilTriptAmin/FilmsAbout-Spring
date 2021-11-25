package by.karelin.domain.utils.enums;

public enum UserRole {
    USER(0),
    ADMIN(1);

    private final int code;
    UserRole(int code) { this.code = code; }
    public int getValue() { return code; }
}
