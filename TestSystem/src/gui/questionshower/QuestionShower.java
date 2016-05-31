package gui.questionshower;

import Question.Question;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public abstract class QuestionShower extends JPanel{
    public QuestionShower(Question questionToShow){
    }
    public abstract void clearInput();
    public abstract boolean isFilled();
}
