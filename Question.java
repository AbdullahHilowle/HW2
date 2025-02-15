package com.hw2;

import java.time.LocalDateTime;

public class Question {
    private String id;
    private String content;
    private LocalDateTime timestamp;
    private boolean resolved;
    private boolean deleted; // Soft delete flag
    
    // Constructor
    public Question(String content) {
        validateContent(content); // Input validation
        this.id = generateUniqueId();
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.resolved = false;
        this.deleted = false; // Default to not deleted
    }
    
    private String generateUniqueId() {
        return "Q" + System.currentTimeMillis();  // Simple unique ID generation
    }

    // Input validation (User Story #2)
    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Question content cannot be empty");
        }
    }
    
    // Soft delete method
    public void softDelete() {
        this.deleted = true;
        this.content = "<Deleted>";
    }

    // CRUD support methods
    public void update(String newContent) { // User Story #7
        validateContent(newContent);
        this.content = newContent;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
    public boolean isDeleted() { return deleted; } // Check if deleted
}
