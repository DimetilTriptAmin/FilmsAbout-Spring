package by.karelin.business.dto.Responses;


import org.springframework.util.StringUtils;

public class ServiceResponse<TValue> {
    private TValue value;
    private String errorMessage;

    //region ctors

    public ServiceResponse(TValue response)
    {
        value = response;
    }

    public ServiceResponse(String message)
    {
        errorMessage = message;
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
}
