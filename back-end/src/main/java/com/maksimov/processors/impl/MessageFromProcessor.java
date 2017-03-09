package com.maksimov.processors.impl;

import com.maksimov.models.entity.LogKey;
import com.maksimov.processors.MessageProcessor;
import org.springframework.stereotype.Component;

/**
 * Created on 02.03.17.
 */
@Component
public class MessageFromProcessor implements MessageProcessor {

    private static final String MESSAGE_FROM_START = "from=<";
    private static final String MESSAGE_FROM_END = ">, ";

    @Override
    public void process(LogKey key, String message) {
        if (message.trim().startsWith(MESSAGE_FROM_START)) {
            key.setStatus(STATUS_PROCESSING);
            key.setEmailFrom(getEmail(message));
        }
    }


    private String getEmail(String message) {
        Integer startIndex = message.indexOf(MESSAGE_FROM_START) + MESSAGE_FROM_START.length();
        Integer endIndex = message.indexOf(MESSAGE_FROM_END);
        return message.substring(startIndex, endIndex);
    }

}
