package gui.questionedit;

import Question.Question;
import Question.QuestionFactory;
import Question.EssayQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/6/2.
 */
public class EssayQuestionFrame extends QuestionFrame {
    private JTextArea promptTextArea;
    private EssayQuestion question;
    private JPanel createMainPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        promptTextArea = new JTextArea();
        ret.add(new JLabel("Essay:"), BorderLayout.NORTH);
        ret.add(promptTextArea, BorderLayout.CENTER);
        return ret;
    }
    public EssayQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        QuestionFactory factory = new QuestionFactory();
        setQuestion(factory.createQuestion(QuestionFactory.QuestionType.ESSAY));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                EssayQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question.setPrompt(promptTextArea.getText());
                EssayQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
    }

    @Override
    public Question getQuestion() {
        return null;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        this.question = (EssayQuestion) questionToShow;
        promptTextArea.setText(questionToShow.getPrompt());
    }
}
