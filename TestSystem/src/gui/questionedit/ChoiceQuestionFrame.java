package gui.questionedit;

import Question.ChoiceQuestion;
import Question.Question;
import Question.QuestionFactory;
import answer.ChoiceAnswer;

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
    private JList<String> answerList;
    private DefaultListModel<String> answerListModel;
    private JPanel createAnswerList(){
        JPanel ret = new JPanel();
        ret.setLayout(new BorderLayout());
        answerListModel = new DefaultListModel<>();
        answerList = new JList<>();
        answerList.setModel(answerListModel);
        JPanel answerBtnPanel = new JPanel(new GridLayout(1,2));
        JButton addToAnswerBtn = new JButton("Add to answer");
        JButton removeFromAnswerBtn =new JButton("Remove from answer");
        addToAnswerBtn.addActionListener((event)->{
            if (itemList.getList().isSelectionEmpty()) return ;
            String itemToAdd = itemList.getListModel().get(itemList.getList().getSelectedIndex());
            if (answerListModel.contains(itemToAdd)) return ;
            answerListModel.addElement(itemToAdd);
        });
        removeFromAnswerBtn.addActionListener((event)->{
            if (answerList.isSelectionEmpty()) return;
            answerListModel.remove(answerList.getSelectedIndex());
        });

        answerBtnPanel.add(addToAnswerBtn);
        answerBtnPanel.add(removeFromAnswerBtn);
        ret.add(answerList, BorderLayout.CENTER);
        ret.add(answerBtnPanel, BorderLayout.NORTH);
        return ret;
    }
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
        if (isHasAnswer()) ret.add(createAnswerList(), BorderLayout.SOUTH);
        return ret;
    }
    public ChoiceQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
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
                if (isHasAnswer()){
                    StringBuilder answerStr = new StringBuilder();
                    for (int i=0;i<answerListModel.size();++i){
                        String answerItem = answerListModel.get(i);
                        for (int j =0;j<itemList.getListModel().size();++j){
                            if (answerItem.equals(itemList.getListModel().get(j))){
                                answerStr.append(j);
                                answerStr.append(" ");
                            }
                        }
                    }
                    question.setAnswer(answerStr.toString().trim());
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
        setMinimumSize(new Dimension(400,300));
        setTitle("Choice Question");
        pack();
        QuestionFactory factory = new QuestionFactory();
        setQuestion(factory.createQuestion(QuestionFactory.QuestionType.CHOICE));
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
        if (isHasAnswer()){
            ChoiceAnswer answer = (ChoiceAnswer) question.getAnswer();
            if (answer!= null) {
                int[] answers = answer.getAnswers();
                for (int ans : answers) {
                    answerListModel.addElement(itemList.getListModel().get(ans));
                }
            }
        }
    }
}
