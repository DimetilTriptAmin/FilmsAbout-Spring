package by.karelin.business.utils;

public enum ErrorCode {
    RepositoryTransactionError(-1);

    private final int code;
    ErrorCode(int code) { this.code = code; }
    public int getValue() { return code; }
}
