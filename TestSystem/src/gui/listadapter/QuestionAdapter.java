package gui.listadapter;

import question.Question;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class QuestionAdapter {
    private Question question;

    public QuestionAdapter(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question.getType()+": "+question.getPrompt();
    }
}
