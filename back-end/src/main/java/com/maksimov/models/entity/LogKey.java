package com.maksimov.models.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created on 01.03.17.
 */
@Entity
@Table(name = "log_key")
public class LogKey {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_event_date", nullable = false)
    @Type(type = "timestamp")
    private Date firstEventDate;

    @Column(name = "logKey", length = 100, nullable = false, unique = true)
    private String logKey;

    @Column(name = "ip", length = 100, nullable = false)
    private String ip;

    @Column(name = "email_from", length = 200)
    private String emailFrom;

    @Column(name = "email_to", length = 2000)
    private String emailTo;

    @Column(name = "status", length = 250)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFirstEventDate() {
        return firstEventDate;
    }

    public void setFirstEventDate(Date firstEventDate) {
        this.firstEventDate = firstEventDate;
    }

    public String getLogKey() {
        return logKey;
    }

    public void setLogKey(String logKey) {
        this.logKey = logKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogKey{" +
                "id=" + id +
                ", firstEventDate=" + firstEventDate +
                ", logKey='" + logKey + '\'' +
                ", ip='" + ip + '\'' +
                ", emailFrom='" + emailFrom + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogKey key = (LogKey) o;
        return Objects.equals(getLogKey(), key.getLogKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogKey());
    }
}
