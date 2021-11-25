package by.karelin.business.dto.Responses;


import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ServiceResponse<TValue> {
    private TValue value;
    private String errorMessage;
    private HttpStatus httpStatus;

    //region ctors

    public ServiceResponse(TValue response, HttpStatus httpStatus)
    {
        value = response;
        this.httpStatus = httpStatus;
    }

    public ServiceResponse(String errorMessage, HttpStatus httpStatus)
    {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    //endregion

    //region getters&setters

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    //endregion

    public boolean IsSucceeded() {
        return !StringUtils.hasLength(errorMessage);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
