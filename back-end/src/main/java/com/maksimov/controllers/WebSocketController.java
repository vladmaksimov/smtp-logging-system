package com.maksimov.controllers;

import com.maksimov.models.view.HelloMessage;
import com.maksimov.models.view.WSMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created on 09.03.17.
 */
@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public WSMessage getLastUpdate(HelloMessage message) {
        return new WSMessage(message.getName());
    }


}
