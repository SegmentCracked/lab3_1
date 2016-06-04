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
        if (question instanceof ChoiceQuestion){
            return new ChoiceQuestionShower(question);
        }
        if (question instanceof RankQuestion){
            return new RankQuestionShower(question);
        }
        if (question instanceof MapQuestion){
            return new MapQuestionShower(question);
        }
        if (question instanceof EssayQuestion){
            return new EssayQuestionShower(question);
        }
        return null;
    }
}
