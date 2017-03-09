package com.maksimov.models.view;

/**
 * Created on 09.03.17.
 */
public class WSMessage {

    public WSMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
