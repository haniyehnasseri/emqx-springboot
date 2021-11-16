package com.example.demo.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

/**
 * @author Haniyeh Nasseri
 */
@Configuration
public class MqttSubscriberConfig {

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.default-topic}")
    private String defaultTopic;

    @Value("${mqtt.default-qos}")
    private int defaultQos;

    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";

    private final MqttConnectionOptionsConfig mqttConnectionOptionsConfig;

    public MqttSubscriberConfig(MqttConnectionOptionsConfig mqttConnectionOptionsConfig) {
        this.mqttConnectionOptionsConfig = mqttConnectionOptionsConfig;
    }

    @Bean
    public MqttConnectOptions getReceiverMqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttConnectionOptionsConfig.getUsername());
        options.setPassword(mqttConnectionOptionsConfig.getPassword().toCharArray());
        options.setServerURIs(new String[]{mqttConnectionOptionsConfig.getServerUri()});
        options.setConnectionTimeout(mqttConnectionOptionsConfig.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConnectionOptionsConfig.getKeepAliveInterval());
        options.setMaxInflight(mqttConnectionOptionsConfig.getMaxInFlight());
        options.setCleanSession(mqttConnectionOptionsConfig.isCleanSession());
        options.setMaxReconnectDelay(mqttConnectionOptionsConfig.getMaxReconnectDelay());
        options.setExecutorServiceTimeout(mqttConnectionOptionsConfig.getExecutorServiceTimeout());
        options.setAutomaticReconnect(mqttConnectionOptionsConfig.isAutomaticReconnect());
        return options;
    }


    @Bean
    public MqttPahoClientFactory receiverMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiverMqttConnectOptions());
        return factory;
    }


    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    @Bean
    public MessageProducer inbound() {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, receiverMqttClientFactory(), defaultTopic);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultQos);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }
}