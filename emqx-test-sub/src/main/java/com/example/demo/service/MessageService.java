package com.example.demo.service;

import com.example.demo.configuration.MqttSubscriberConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

/**
 * @author Haniyeh Nasseri
 */
@Service
@Slf4j
public class MessageService {

    @Bean
    @ServiceActivator(inputChannel = MqttSubscriberConfig.CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String msg = message.getPayload().toString();
                log.info("\n--------------------START-------------------\n" +
                        "Receive subscription message: \n topic:" + topic + "\n MESSAGE:" + msg +
                        "\n---------------------END--------------------");
            }
        };
    }

}
