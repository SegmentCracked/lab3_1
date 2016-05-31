package gui.questionframe;

import Question.ChoiceQuestion;
import Question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class ChoiceQuestionFrame extends QuestionFrame {
    private ChoiceQuestion question;
    private JTextField promptTextField;
    private ItemList itemList;
    private JPanel createMainPanel(){
        JPanel ret =new JPanel(new BorderLayout());
        JPanel topPanel =new JPanel(new GridLayout(3,1));
        topPanel.add(new JLabel("Prompt:"));
        promptTextField=new JTextField();
        topPanel.add(promptTextField);
        topPanel.add(new JLabel("Choices:"));
        ret.add(topPanel,BorderLayout.NORTH);
        itemList = new ItemList(ChoiceQuestionFrame.this);
        ret.add(itemList.getList(), BorderLayout.CENTER);
        return ret;
    }
    public ChoiceQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        question = new ChoiceQuestion();
        setLayout(new BorderLayout());
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createMainPanel(),BorderLayout.CENTER);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                ChoiceQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question.setPrompt(promptTextField.getText());
                question.clearItem();
                for (int i=0;i<itemList.getListModel().size();++i){
                    question.addItem(itemList.getListModel().get(i));
                }
                ChoiceQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
        setMinimumSize(new Dimension(300,200));
        setTitle("Choice Question");
        pack();
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        this.question=(ChoiceQuestion) questionToShow;
        this.promptTextField.setText(this.question.getPrompt());
        java.util.List<String> items = this.question.getItem();
        for (String s: items){
            itemList.getListModel().addElement(s);
        }
    }
}
