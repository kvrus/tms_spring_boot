package ru.moscow.tms.tms.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.moscow.tms.tms.models.GreetingMessage;
import ru.moscow.tms.tms.models.NotificationMessage;

@Controller
public class NotificationController {

    @MessageMapping("/hello")
    @SendTo("/topic/notifications")
    public NotificationMessage notification(GreetingMessage message) {
        return new NotificationMessage("Hello " + HtmlUtils.htmlEscape(message.getMessage()));
    }

}
