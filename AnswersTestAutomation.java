package com.hw2;

import java.util.List;

public class AnswersTestAutomation {
    static int numPassed = 0;
    static int numFailed = 0;
    private static Answers answers = new Answers();
    private static String testQuestionId = "Q_TEST";

    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nTesting Automation for Answers");

        // Test Answer Validation
        testAnswerValidation();
        
        // Test Answers CRUD
        testAddAnswer();
        testUpdateAnswer();
        testDeleteAnswer();
        testSearchAnswers();
        testQuestionAnswerMapping();

        System.out.println("____________________________________________________________________________");
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    // Test Cases for Answer Validation
    private static void testAnswerValidation() {
        System.out.println("\n--- Testing Answer Validation ---");
        testCase("Valid answer content", "How to write tests", true);
        testCase("Empty answer content", "", false);
    }

    // Test Cases for Answers CRUD
    private static void testAddAnswer() {
        System.out.println("\n--- Testing Add Answer ---");
        try {
            Answer a = answers.addAnswer(testQuestionId, "Test answer");
            if (answers.getAnswer(a.getId()) != null) {
                System.out.println("***Success*** Answer added!");
                numPassed++;
            } else {
                System.out.println("***Failure*** Answer not found");
                numFailed++;
            }
        } catch (Exception e) {
            System.out.println("***Failure*** " + e.getMessage());
            numFailed++;
        }
    }

    private static void testUpdateAnswer() {
        System.out.println("\n--- Testing Update Answer ---");
        Answer a = answers.addAnswer(testQuestionId, "Original answer");
        try {
            answers.updateAnswer(a.getId(), "Updated answer");
            if (a.getContent().equals("Updated answer")) {
                System.out.println("***Success*** Answer updated!");
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

    private static void testDeleteAnswer() {
        System.out.println("\n--- Testing Delete Answer ---");
        Answer a = answers.addAnswer(testQuestionId, "Answer to delete");
        boolean deleted = answers.deleteAnswer(a.getId());
        if (deleted && answers.getAnswer(a.getId()) == null) {
            System.out.println("***Success*** Answer deleted!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Deletion failed");
            numFailed++;
        }
    }

    private static void testSearchAnswers() {
        System.out.println("\n--- Testing Search Answers ---");
        answers.addAnswer(testQuestionId, "How to debug Java?");
        List<Answer> results = answers.searchAnswers("debug");
        if (!results.isEmpty()) {
            System.out.println("***Success*** Search found answers!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Search failed");
            numFailed++;
        }
    }

    private static void testQuestionAnswerMapping() {
        System.out.println("\n--- Testing Question-Answer Mapping ---");
        Answer a = answers.addAnswer(testQuestionId, "Mapping test answer");
        List<Answer> mappedAnswers = answers.getAnswersForQuestion(testQuestionId);
        if (mappedAnswers.contains(a)) {
            System.out.println("***Success*** Answer mapped to question!");
            numPassed++;
        } else {
            System.out.println("***Failure*** Mapping failed");
            numFailed++;
        }
    }

    // Helper method
    private static void testCase(String description, String content, boolean shouldPass) {
        System.out.println("\nTest case: " + description);
        System.out.println("Input: \"" + content + "\"");
        try {
            new Answer("Q1", content);
            if (shouldPass) {
                System.out.println("***Success*** Valid answer created");
                numPassed++;
            } else {
                System.out.println("***Failure*** Invalid answer allowed");
                numFailed++;
            }
        } catch (IllegalArgumentException e) {
            if (!shouldPass) {
                System.out.println("***Success*** Invalid answer rejected: " + e.getMessage());
                numPassed++;
            } else {
                System.out.println("***Failure*** Valid answer rejected: " + e.getMessage());
                numFailed++;
            }
        }
    }
}
