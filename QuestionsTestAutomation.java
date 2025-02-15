package com.hw2;

import java.util.List;

public class QuestionsTestAutomation {
    static int numPassed = 0;
    static int numFailed = 0;
    private static Questions questions = new Questions();

    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nTesting Automation for HW2");

        // Test Question Validation
        testQuestionValidation();
        
        // Test Questions CRUD
        testAddQuestion();
        testUpdateQuestion();
        testDeleteQuestion();
        testSearchQuestions();
        testUnresolvedQuestions();

        System.out.println("____________________________________________________________________________");
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    // Test Cases for Question Validation
    private static void testQuestionValidation() {
        System.out.println("\n--- Testing Question Validation ---");
        testCase("Valid question content", "How to use Java?", true);
        testCase("Empty question content", "", false);
    }

    // Test Cases for Questions CRUD
    private static void testAddQuestion() {
        System.out.println("\n--- Testing Add Question ---");
        try {
            Question q = questions.addQuestion("Test question");
            if (questions.getAllQuestions().contains(q)) {
                System.out.println("***Success*** Question added!");
                numPassed++;
            } else {
                System.out.println("***Failure*** Question not found in list");
                numFailed++;
            }
        } catch (Exception e) {
            System.out.println("***Failure*** " + e.getMessage());
            numFailed++;
        }
    }

    private static void testUpdateQuestion() {
        System.out.println("\n--- Testing Update Question ---");
        Question q = questions.addQuestion("Original question");
        try {
            questions.updateQuestion(q.getId(), "Updated question");
            if (q.getContent().equals("Updated question")) {
                System.out.println("***Success*** Question updated!");
                numPassed++;
            } else {
                System.out.println("***Failure*** Update failed");
                numFailed++;
            }
        } catch (Exception e) {
            System.out.println("***Failure*** " + e.getMessage());
            numFailed++;
        }
    }

    private static void testDeleteQuestion() {
        System.out.println("\n--- Testing Delete Question ---");
        Question q = questions.addQuestion("Question to delete");
        boolean deleted = questions.softDeleteQuestion(q.getId());
        if (deleted && !questions.getAllQuestions().contains(q)) {
            System.out.println("***Success*** Question deleted!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Deletion failed");
            numFailed++;
        }
    }

    private static void testSearchQuestions() {
        System.out.println("\n--- Testing Search Questions ---");
        questions.addQuestion("How to debug Java?");
        List<Question> results = questions.searchQuestions("debug");
        if (!results.isEmpty()) {
            System.out.println("***Success*** Search found questions!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Search failed");
            numFailed++;
        }
    }

    private static void testUnresolvedQuestions() {
        System.out.println("\n--- Testing Unresolved Questions ---");
        Question q = questions.addQuestion("Unresolved question");
        List<Question> unresolved = questions.getUnresolvedQuestions();
        if (unresolved.contains(q)) {
            System.out.println("***Success*** Unresolved question listed!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Unresolved not detected");
            numFailed++;
        }
    }

    // Helper method for validation tests
    private static void testCase(String description, String content, boolean shouldPass) {
        System.out.println("\nTest case: " + description);
        System.out.println("Input: \"" + content + "\"");
        try {
            new Question(content);
            if (shouldPass) {
                System.out.println("***Success*** Valid question created");
                numPassed++;
            } else {
                System.out.println("***Failure*** Invalid question allowed");
                numFailed++;
            }
        } catch (IllegalArgumentException e) {
            if (!shouldPass) {
                System.out.println("***Success*** Invalid question rejected: " + e.getMessage());
                numPassed++;
            } else {
                System.out.println("***Failure*** Valid question rejected: " + e.getMessage());
                numFailed++;
            }
        }
    }
}