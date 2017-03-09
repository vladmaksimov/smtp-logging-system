package com.maksimov.models.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;


/**
 * Created on 01.03.17.
 */
@Entity
@Table(name = "log_details")
public class LogDetail {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", length = 2000, nullable = false)
    private String message;

    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    @Column(name = "full_details", length = 2000, nullable = false, unique = true)
    private String fullDetails;

    @Column(name = "event_time", nullable = false)
    @Type(type = "timestamp")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private LogKey logKey;


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

    public LogKey getLogKey() {
        return logKey;
    }

    public void setLogKey(LogKey logKey) {
        this.logKey = logKey;
    }

    @Override
    public String toString() {
        return "LogDetail{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", fullDetails='" + fullDetails + '\'' +
                ", date=" + date +
                '}';
    }
}
