package gui.questionshower;

import Question.*;

/**
 * Created by Mengxiao Lin on 2016/6/1.
 */
public class QuestionShowerFactory {
    public QuestionShower createQuestionShowerByQuestion(Question question){
        if (question instanceof DecideQuestion){
            return new DecideQuestionShower(question);
        }
        return null;
    }
}
