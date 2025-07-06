package com.example.etudiantapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationServiceConfig {

    @Value("${notification.service.base-url:http://localhost:8081}")
    private String baseUrl;

    @Value("${notification.service.timeout:5000}")
    private int timeout;

    @Value("${notification.service.retry-attempts:3}")
    private int retryAttempts;

    @Value("${notification.service.api-key:}")
    private String apiKey;

    @Value("${notification.service.enabled:true}")
    private boolean enabled;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Getters and Setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
