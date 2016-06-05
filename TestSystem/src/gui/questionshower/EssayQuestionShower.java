package gui.questionshower;

import Question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class EssayQuestionShower extends QuestionShower {
    private JTextArea promptLabel;
    private JTextArea answerTextArea;
    public EssayQuestionShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new BorderLayout());
        promptLabel = new JTextArea(questionToShow.getPrompt());
        promptLabel.setEditable(false);
        promptLabel.setBackground(this.getBackground());
        promptLabel.setLineWrap(true);
        answerTextArea = new JTextArea();
        answerTextArea.setLineWrap(true);
        add(new JScrollPane(promptLabel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.NORTH);
        add(new JScrollPane(answerTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }

    @Override
    public void clearInput() {
        answerTextArea.setText("");
    }

    @Override
    public boolean isFilled() {
        return answerTextArea.getText().length()!=0;
    }

    @Override
    public String getAnswer() {
        return answerTextArea.getText();
    }
}
