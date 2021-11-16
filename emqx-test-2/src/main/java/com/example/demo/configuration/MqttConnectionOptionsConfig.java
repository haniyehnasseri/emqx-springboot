package com.example.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Haniyeh Nasseri
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mqtt.connection.options")
public class MqttConnectionOptionsConfig {

    private String serverUri;

    private String username;

    private String password;

    private int keepAliveInterval;

    private int maxInFlight;

    private int connectionTimeout;

    private int maxReconnectDelay;

    private int executorServiceTimeout;

    private boolean cleanSession;

    private boolean automaticReconnect;

}