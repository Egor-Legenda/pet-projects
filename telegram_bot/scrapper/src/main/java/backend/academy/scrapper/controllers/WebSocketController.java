package backend.academy.scrapper.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller // ДЕМКА не работает
public class WebSocketController {

    @MessageMapping("/update/{userId}")
    @SendTo("/topic/updates/{userId}")
    public String sendUpdate(@DestinationVariable Long userId, String message) {

        return "{\"message\": \"Обновление: " + message + "\"}";
    }
}
