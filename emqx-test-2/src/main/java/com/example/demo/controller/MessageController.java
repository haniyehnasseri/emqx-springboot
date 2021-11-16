package com.example.demo.controller;

import com.example.demo.service.MessageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haniyeh Nasseri
 */
@RestController
public class MessageController {

    @Value("${mqtt.default-topic}")
    private String defaultTopic;

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/send")
    public void sendMsg(@RequestParam String data) {
        LoggerFactory.getLogger(MessageController.class).info(data);
        messageService.sendMessage(defaultTopic, data.getBytes());
    }



}
