package com.maksimov.models.view;

import java.util.Date;

/**
 * Created on 03.03.17.
 */
public class LogDetailView {

    private Long id;
    private String message;
    private String eventType;
    private String fullDetails;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getFullDetails() {
        return fullDetails;
    }

    public void setFullDetails(String fullDetails) {
        this.fullDetails = fullDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
