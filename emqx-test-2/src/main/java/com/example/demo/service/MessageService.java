package com.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

/**
 * @author Haniyeh Nasseri
 */
@Log4j2
@Service
public class MessageService{

    private final MessageHandler messageHandler;

    @Autowired
    public MessageService(@Qualifier("mqttOutbound") MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public synchronized void sendMessage(String topic,
                                         byte[] payload) throws MessagingException {
        Message<?> message = MessageBuilder.withPayload(payload).setHeader(MqttHeaders.TOPIC, topic).build();
        messageHandler.handleMessage(message);
    }
}
