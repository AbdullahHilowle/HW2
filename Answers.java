package com.hw2;

import java.util.*;
import java.time.LocalDateTime;

public class Answers {
    private Map<String, Answer> answers;
    private Map<String, List<String>> questionAnswers;
    private Map<String, List<String>> answerReplies;
    
    public Answers() {
        this.answers = new HashMap<>();
        this.questionAnswers = new HashMap<>();
        this.answerReplies = new HashMap<>();
    }
    
    public boolean softDeleteAnswer(String answerId) {
        Answer answer = answers.get(answerId);
        if (answer != null) {
            answer.softDelete();
            return true;
        }
        return false;
    }
    
    public Answer addAnswer(String questionId, String content) {
        Answer answer = new Answer(questionId, content);
        answers.put(answer.getId(), answer);
        questionAnswers.computeIfAbsent(questionId, k -> new ArrayList<>()).add(answer.getId());
        return answer;
    }
    
    public Answer addSubAnswer(String parentAnswerId, String content) {
        Answer subAnswer = new Answer(answers.get(parentAnswerId).getQuestionId(), content);
        answers.put(subAnswer.getId(), subAnswer);
        answerReplies.computeIfAbsent(parentAnswerId, k -> new ArrayList<>()).add(subAnswer.getId());
        return subAnswer;
    }
    
    public List<Answer> getAnswersForQuestion(String questionId) {
        List<String> answerIds = questionAnswers.getOrDefault(questionId, new ArrayList<>());
        return answerIds.stream().map(answers::get).filter(Objects::nonNull).toList();
    }
    
    public Answer getAnswer(String answerId) {
        return answers.get(answerId);
    }
    
    public void updateAnswer(String answerId, String newContent) {
        Answer answer = answers.get(answerId);
        if (answer != null && !answer.isDeleted()) {
            answer.update(newContent);
        } else {
            throw new IllegalArgumentException("Answer not found or deleted");
        }
    }
    
    public boolean deleteAnswer(String answerId) {
        return answers.remove(answerId) != null;
    }
    
    public List<Answer> searchAnswers(String keyword) {
        return answers.values().stream()
                      .filter(a -> a.getContent().toLowerCase().contains(keyword.toLowerCase()))
                      .toList();
    }

}