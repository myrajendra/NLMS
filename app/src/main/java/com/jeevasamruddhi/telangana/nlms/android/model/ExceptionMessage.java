package com.jeevasamruddhi.telangana.nlms.android.model;

/**
 * Created by jaganmohan on 31/12/16.
 */

public class ExceptionMessage extends BaseModel
{
    private Boolean status;
    private String title;
    private String message;

    public ExceptionMessage(){}

    public ExceptionMessage(boolean status, String title, String message)
    {
        this.status = status;
        this.title = title;
        this.message = message;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
