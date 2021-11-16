package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @author Haniyeh Nasseri
 */
@Configuration
public class OutboundChannelConfig {

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.default-topic}")
    private String defaultTopic;

    @Value("${mqtt.default-qos}")
    private int defaultQos;

    @Value("${mqtt.default-retained}")
    private boolean defaultRetained;

    private final MqttPahoClientFactory mqttClientFactory;

    public OutboundChannelConfig(MqttPahoClientFactory mqttClientFactory) {
        this.mqttClientFactory = mqttClientFactory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory);
        messageHandler.setDefaultTopic(defaultTopic);
        messageHandler.setDefaultQos(defaultQos);
        messageHandler.setDefaultRetained(defaultRetained);
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

}