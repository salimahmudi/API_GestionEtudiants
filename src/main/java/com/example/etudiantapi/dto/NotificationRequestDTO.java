package com.example.etudiantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotificationRequestDTO {

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("recipient")
    private String recipient;

    @NotBlank(message = "Subject is required")
    @JsonProperty("subject")
    private String subject;

    @NotBlank(message = "Message is required")
    @JsonProperty("message")
    private String message;

    @NotNull(message = "Notification type is required")
    @JsonProperty("type")
    private NotificationType type;

    @JsonProperty("channel")
    private NotificationChannel channel = NotificationChannel.EMAIL;

    @JsonProperty("priority")
    private Priority priority = Priority.NORMAL;

    @JsonProperty("template")
    private String template;

    @JsonProperty("language")
    private String language = "en";

    public enum NotificationType {
        STUDENT_CREATED,
        STUDENT_UPDATED,
        STUDENT_DELETED,
        WELCOME,
        REMINDER,
        ALERT
    }

    public enum NotificationChannel {
        EMAIL,
        SMS,
        PUSH,
        IN_APP
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        URGENT
    }

    // Constructors
    public NotificationRequestDTO() {}

    public NotificationRequestDTO(String recipient, String subject, String message, NotificationType type) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
    }

    // Getters and Setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
