package com.example.demo.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.util.StringUtils;

/**
 * @author Haniyeh Nasseri
 */
@Configuration
public class MqttPahoClientFactoryConfig {

    private final MqttConnectionOptionsConfig connectionOptions;

    @Autowired
    public MqttPahoClientFactoryConfig(MqttConnectionOptionsConfig connectionOptions) {
        this.connectionOptions = connectionOptions;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{connectionOptions.getServerUri()});
        if (!StringUtils.isEmpty(connectionOptions.getUsername())) {
            options.setUserName(connectionOptions.getUsername());
        } else {
            options.setUserName("");
        }
        if (!StringUtils.isEmpty(connectionOptions.getPassword())) {
            options.setPassword(connectionOptions.getPassword().toCharArray());
        }
        options.setKeepAliveInterval(connectionOptions.getKeepAliveInterval());
        options.setMaxInflight(connectionOptions.getMaxInFlight());
        options.setConnectionTimeout(connectionOptions.getConnectionTimeout());
        options.setMaxReconnectDelay(connectionOptions.getMaxReconnectDelay());
        options.setExecutorServiceTimeout(connectionOptions.getExecutorServiceTimeout());
        options.setCleanSession(connectionOptions.isCleanSession());
        options.setAutomaticReconnect(connectionOptions.isAutomaticReconnect());
        factory.setConnectionOptions(options);
        return factory;
    }

}
