package com.example.etudiantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class NotificationResponseDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private NotificationStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("type")
    private String type;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("deliveredAt")
    private LocalDateTime deliveredAt;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("retryCount")
    private Integer retryCount;

    public enum NotificationStatus {
        PENDING,
        SENT,
        DELIVERED,
        FAILED,
        CANCELLED
    }

    // Constructors
    public NotificationResponseDTO() {}

    public NotificationResponseDTO(String id, NotificationStatus status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}
