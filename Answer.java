package com.hw2;

import java.time.LocalDateTime;

public class Answer {
    private String id;
    private String questionId;
    private String content;
    private LocalDateTime timestamp;
    private boolean deleted;
    
    public Answer(String questionId, String content) {
        validateContent(content);
        this.id = generateUniqueId();
        this.questionId = questionId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.deleted = false;
    }
    
    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer content cannot be empty");
        }
    }
    
    public void update(String newContent) {
        validateContent(newContent);
        this.content = newContent;
    }
    
    public void softDelete() {
        this.content = "<Deleted>";
        this.deleted = true;
    }
    
    public boolean isDeleted() { return deleted; }
    public String getId() { return id; }
    public String getQuestionId() { return questionId; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    private String generateUniqueId() {
        return "A" + System.currentTimeMillis();
    }
}