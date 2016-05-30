package gui;

import question.Question;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class QuestionAdapter {
    private Question question;

    public QuestionAdapter(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return question.getType()+": "+question.getPrompt();
    }
}
