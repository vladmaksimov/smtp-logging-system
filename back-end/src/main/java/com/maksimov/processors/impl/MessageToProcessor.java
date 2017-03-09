package com.maksimov.processors.impl;

import com.maksimov.models.entity.LogKey;
import com.maksimov.processors.MessageProcessor;
import org.springframework.stereotype.Component;

/**
 * Created on 02.03.17.
 */
@Component
public class MessageToProcessor implements MessageProcessor {

    private static final String MESSAGE_TO_START = "to=<";
    private static final String MESSAGE_TO_END = ">, ";
    private static final String MESSAGE_STATUS = "status=";
    private static final String MESSAGE_EMAIL_SEPARATOR = ", ";
    private static final String MESSAGE_WORD_SEPARATOR = " ";

    @Override
    public void process(LogKey key, String message) {
        if (message.trim().startsWith(MESSAGE_TO_START)) {
            key.setStatus(getStatus(message));
            key.setEmailTo(setEmail(key, message));
        }
    }

    private String setEmail(LogKey key, String message) {
        String email = getEmail(message);
        String emails = key.getEmailTo();

        if (emails == null) {
            return email;
        } else {
            return emails.contains(email) ? email : emails.concat(MESSAGE_EMAIL_SEPARATOR).concat(email);
        }
    }

    private String getEmail(String message) {
        Integer startIndex = message.indexOf(MESSAGE_TO_START) + MESSAGE_TO_START.length();
        Integer endIndex = message.indexOf(MESSAGE_TO_END);
        return message.substring(startIndex, endIndex);
    }

    private String getStatus(String message) {
        if (message.contains(MESSAGE_STATUS)) {
            String messageFromStatus = message.substring(message.indexOf(MESSAGE_STATUS));
            String status = messageFromStatus.substring(MESSAGE_STATUS.length(), messageFromStatus.indexOf(MESSAGE_WORD_SEPARATOR));
            return status.toUpperCase();
        } else {
            return STATUS_UNKNOWN;
        }

    }

}
