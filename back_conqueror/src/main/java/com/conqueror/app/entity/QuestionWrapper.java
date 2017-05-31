package com.conqueror.app.entity;

/**
 * @author Bogdan Kaftanatiy
 */
public class QuestionWrapper {
    private String question;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setAnswer(int i, String answer) {
        switch (i) {
            case 0:
                setAnswer1(answer);
                break;
            case 1:
                setAnswer2(answer);
                break;
            case 2:
                setAnswer3(answer);
                break;
            case 3:
                setAnswer4(answer);
                break;
        }
    }

    public void addAnswer(String answer) {
        if (answer1 == null)
            setAnswer1(answer);
        else if (answer2 == null)
            setAnswer2(answer);
        else if (answer3 == null)
            setAnswer3(answer);
        else if (answer4 == null)
            setAnswer4(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionWrapper that = (QuestionWrapper) o;

        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (answer1 != null ? !answer1.equals(that.answer1) : that.answer1 != null) return false;
        if (answer2 != null ? !answer2.equals(that.answer2) : that.answer2 != null) return false;
        if (answer3 != null ? !answer3.equals(that.answer3) : that.answer3 != null) return false;
        return answer4 != null ? answer4.equals(that.answer4) : that.answer4 == null;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answer1 != null ? answer1.hashCode() : 0);
        result = 31 * result + (answer2 != null ? answer2.hashCode() : 0);
        result = 31 * result + (answer3 != null ? answer3.hashCode() : 0);
        result = 31 * result + (answer4 != null ? answer4.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionWrapper{" +
                "question='" + question + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", answer4='" + answer4 + '\'' +
                '}';
    }
}
