package com.maksimov.service.impl;

import com.maksimov.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created on 10.03.17.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage() {
        this.messagingTemplate.convertAndSend("/topic/key/update", "update");
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
}
