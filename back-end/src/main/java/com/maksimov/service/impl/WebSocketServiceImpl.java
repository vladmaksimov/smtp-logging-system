package com.maksimov.service.impl;

import com.maksimov.models.entity.LogKey;
import com.maksimov.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created on 10.03.17.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private static final String CHANNEL_KEY = "/topic/key/update";
    private static final String CHANNEL_DETAILS = "/topic/key/update/%s";
    private static final String CHANNEL_MESSAGE = "update";

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(Collection<LogKey> keys) {
        messagingTemplate.convertAndSend(CHANNEL_KEY, CHANNEL_MESSAGE);

        for (LogKey key : keys) {
            messagingTemplate.convertAndSend(String.format(CHANNEL_DETAILS, key.getId()), CHANNEL_MESSAGE);
        }
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
}
