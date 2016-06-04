package gui.questionedit;

import Question.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public abstract class QuestionFrame extends JDialog{
    /**
     * 获取本问题窗口所编辑的问题
     * @return Question对象
     */
    public abstract Question getQuestion();

    /**
     * 要编辑的问题，用于显示一个已经存在的问题
     * @param questionToShow 要被展示并编辑的问题对象
     */
    public abstract void setQuestion(Question questionToShow);

    /**
     * 需要由子类重写，参数是是否提供答案
     * @param hasAnswer
     */
    public QuestionFrame(boolean hasAnswer){
        this.hasAnswer = hasAnswer;
        setModal(true);
    }
    private boolean hasAnswer;

    public boolean isHasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(boolean hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

    protected JButton cancelBtn;
    protected JButton finishBtn;
    protected JSpinner scoreSpinner;
    protected JPanel createBottomPanel() {
        JPanel ret = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        this.cancelBtn = new JButton("Cancel");
        this.finishBtn = new JButton("Finish");
        btnPanel.add(cancelBtn);
        btnPanel.add(finishBtn);
        ret.add(btnPanel, BorderLayout.EAST);
        JPanel scorePanel = new JPanel((new BorderLayout()));
        scorePanel.add(new JLabel("Score:"), BorderLayout.WEST);
        scoreSpinner = new JSpinner();
        JSpinner.NumberEditor scoreEditor = new JSpinner.NumberEditor(scoreSpinner);
        scoreSpinner.setEditor(scoreEditor);
        scoreSpinner.setValue(1);
        scoreEditor.getModel().setMinimum(0);
        scorePanel.add(scoreSpinner, BorderLayout.CENTER);
        if (hasAnswer) {
            ret.add(scorePanel, BorderLayout.WEST);
        }
        return ret;
    }

    public static QuestionFrame createFrameByQuestion(Question question,boolean hasAnswer){
        QuestionFrame ret= null;
        if (question instanceof DecideQuestion){
            ret = new DecideQuestionFrame(hasAnswer);
        }
        if (question instanceof ChoiceQuestion){
            ret = new ChoiceQuestionFrame(hasAnswer);
        }
        if (question instanceof RankQuestion){
            ret = new RankQuestionFrame(hasAnswer);
        }
        if (question instanceof MapQuestion){
            ret = new MapQuestionFrame(hasAnswer);
        }
        if (question instanceof EssayQuestion){
            ret = new EssayQuestionFrame(hasAnswer);
        }
        if (ret!=null){
            ret.setQuestion(question);
        }
        return ret;
    }
    public int getScoreValue(){
        return (Integer) scoreSpinner.getValue();
    }
}
