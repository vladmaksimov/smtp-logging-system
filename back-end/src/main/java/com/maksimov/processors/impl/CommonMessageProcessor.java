package com.maksimov.processors.impl;

import com.maksimov.models.entity.LogKey;
import com.maksimov.processors.MessageProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 02.03.17.
 */
@Component
public class CommonMessageProcessor implements MessageProcessor {

    private List<String> specificMessages;

    @Override
    public void process(LogKey key, String message) {
        String status = key.getStatus();

        if (status == null) {
            Boolean isCommonMessage = true;
            for (String specificMessage : specificMessages) {
                if (message.trim().startsWith(specificMessage)) {
                    isCommonMessage = false;
                }
            }

            if (isCommonMessage) {
                key.setStatus(STATUS_PROCESSING);
            }
        }
    }

    @Resource(name = "specificMessages")
    public void setSpecificMessages(List<String> specificMessages) {
        this.specificMessages = specificMessages;
    }
}
