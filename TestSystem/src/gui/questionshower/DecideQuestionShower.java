package gui.questionshower;

import Question.Question;
import answer.Answer;
import answer.AnswerFactory;
import answer.DecideAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mengxiao Lin on 2016/6/1.
 */
public class DecideQuestionShower extends QuestionShower {
    private JLabel promptLabel;
    private JToggleButton trueBtn,falseBtn;
    public DecideQuestionShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new BorderLayout());
        promptLabel = new JLabel(questionToShow.getPrompt());
        JPanel btnPanel =new JPanel(new GridLayout(2,1));
        trueBtn = new JToggleButton("Yes");
        falseBtn = new JToggleButton("No");
        trueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                falseBtn.setSelected(false);
            }
        });
        falseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trueBtn.setSelected(false);
            }
        });
        btnPanel.add(trueBtn);
        btnPanel.add(falseBtn);
        add(promptLabel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
    }

    @Override
    public void clearInput() {
        trueBtn.setSelected(false);
        falseBtn.setSelected(false);
    }

    @Override
    public boolean isFilled() {
        return trueBtn.isSelected() || falseBtn.isSelected();
    }

    @Override
    public Answer getAnswer() {
        if (!isFilled()) return null;
        AnswerFactory factory = new AnswerFactory();
        DecideAnswer answer = (DecideAnswer) factory.createAnswer(AnswerFactory.AnswerType.DECIDE);
        if (trueBtn.isSelected()){
            answer.setAnswer("True");
        }
        if (falseBtn.isSelected()){
            answer.setAnswer("False");
        }
        return answer;
    }
}
