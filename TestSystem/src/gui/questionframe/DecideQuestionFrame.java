package gui.questionframe;

import question.DecideQuestion;
import question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class DecideQuestionFrame extends QuestionFrame {
    private DecideQuestion question;
    private JTextField promptTextField;
    private JCheckBox decideAnswer;
    private JPanel createMainPanel(){
        int itemCount = 2;
        if (isHasAnswer()) itemCount+=1;
        JPanel mainPanel = new JPanel(new GridLayout(itemCount,1));
        mainPanel.add(new JLabel("Prompt:"));
        promptTextField =new JTextField("");
        mainPanel.add(promptTextField);
        if (isHasAnswer()){
            decideAnswer =new JCheckBox("Answer:");
            mainPanel.add(decideAnswer);
        }
        return mainPanel;
    }
    public DecideQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        question = new DecideQuestion();
        setLayout(new BorderLayout());
        add(createBottomPanel(),BorderLayout.SOUTH);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                DecideQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question.setPrompt(promptTextField.getText());
                DecideQuestionFrame.this.setVisible(false);
            }
        });
        add(createMainPanel(),BorderLayout.CENTER);
        setMinimumSize(new Dimension(300,0));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
        pack();
        setTitle("Decide Question");
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        DecideQuestion decideQuestion = (DecideQuestion) questionToShow;
        this.question = decideQuestion;
        promptTextField.setText(decideQuestion.getPrompt());
    }
}
