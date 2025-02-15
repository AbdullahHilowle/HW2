package com.hw2;

import java.util.*;

public class Questions {
    private Map<String, Question> questions;
    private Map<String, String> preferredAnswers; // Maps questionId to preferred answerId
    
    public Questions() {
        this.questions = new HashMap<>();
        this.preferredAnswers = new HashMap<>();
    }

    // Create (User Story #1)
    public Question addQuestion(String content) {
        Question question = new Question(content);
        questions.put(question.getId(), question);
        return question;
    }
    
    // Read (User Story #5, #6)
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions.values());
    }
    
    public List<Question> getUnresolvedQuestions() {
        return questions.values().stream()
                       .filter(q -> !q.isResolved())
                       .toList();
    }
    
    // Update (User Story #7, #9)
    public void updateQuestion(String id, String newContent) {
        Question question = questions.get(id);
        if (question == null) {
            throw new IllegalArgumentException("Question not found");
        }
        question.update(newContent);
    }
    
    public void markQuestionAsResolved(String id) {
        Question question = questions.get(id);
        if (question != null) {
            question.setResolved(true);
        }
    }
    
    public void setPreferredAnswer(String questionId, String answerId) {
        if (!questions.containsKey(questionId)) {
            throw new IllegalArgumentException("Question not found");
        }
        preferredAnswers.put(questionId, answerId);
    }
    
    public String getPreferredAnswer(String questionId) {
        return preferredAnswers.get(questionId);
    }
    
    // Soft Delete (User Story #10, #12)
    public boolean softDeleteQuestion(String id) {
        Question question = questions.get(id);
        if (question != null) {
            question.softDelete();
            return true;
        }
        return false;
    }
    
    public boolean deleteQuestionWithConfirmation(String id) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Are you sure you want to delete this question? (Y/N): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                return softDeleteQuestion(id);
            } else {
                System.out.println("Deletion cancelled.");
                return false;
            }
        } finally {
            scanner.close();
        }
    }
    
    // Additional methods
    public List<Question> searchQuestions(String keyword) {
        return questions.values().stream()
               .filter(q -> q.getContent().toLowerCase().contains(keyword.toLowerCase()))
               .toList();
    }

}