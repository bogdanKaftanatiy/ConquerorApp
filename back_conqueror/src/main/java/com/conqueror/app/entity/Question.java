package com.conqueror.app.entity;

import javax.persistence.*;

/**
 * @author Bogdan Kaftanatiy
 */
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "question", nullable = false, unique = true)
    private String question;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "answer", nullable = false)
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (!question.equals(question1.question)) return false;
        if (!category.equals(question1.category)) return false;
        return answer.equals(question1.answer);
    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + answer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", category='" + category + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
