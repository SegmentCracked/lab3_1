package gui.questionedit;

import Question.Question;
import Question.RankQuestion;
import Question.QuestionFactory;
import answer.RankAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class RankQuestionFrame extends QuestionFrame {
    private RankQuestion question;
    private JTextField promptTextField;
    private ItemList choiceList, answerList;
    private JPanel choicePanel, answerPanel, listPanel;
    private void createListPanel(){
        choicePanel = new JPanel(new BorderLayout());
        choicePanel.add(new JLabel("Choices:"),BorderLayout.NORTH);
        choiceList = new ItemList(RankQuestionFrame.this);
        choicePanel.add(choiceList.getList(), BorderLayout.CENTER);
        answerPanel = new JPanel(new BorderLayout());
        answerPanel.add(new JLabel("Answer:"),BorderLayout.NORTH);
        answerList = new ItemList(RankQuestionFrame.this);
        answerPanel.add(answerList.getList(),BorderLayout.CENTER);

        answerList.hideAddMenuItem();
        answerList.hideRemoveMenuItem();
        choiceList.addItemAddListener(item->{
            answerList.getListModel().addElement(item);
            return null;
        });
        choiceList.addItemRemoveListener(item ->{
            answerList.getListModel().removeElement(item);
            return null;
        });

        if (isHasAnswer()) {
            listPanel = new JPanel(new GridLayout(2, 1));
            listPanel.add(choicePanel);
            listPanel.add(answerPanel);
        }else{
            listPanel=choicePanel;
        }
    }
    private JPanel createMainPanel(){
        JPanel ret =new JPanel(new BorderLayout());
        JPanel topPanel =new JPanel(new GridLayout(2,1));
        topPanel.add(new JLabel("Prompt:"));
        promptTextField=new JTextField();
        topPanel.add(promptTextField);
        ret.add(topPanel,BorderLayout.NORTH);
        createListPanel();
        ret.add(listPanel, BorderLayout.CENTER);
        return ret;
    }
    public RankQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        promptTextField = new JTextField();
        setLayout(new BorderLayout());
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createMainPanel(),BorderLayout.CENTER);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                RankQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question.setPrompt(promptTextField.getText());
                question.clearItem();
                for (int i=0;i<choiceList.getListModel().size();++i){
                    question.addItem(choiceList.getListModel().get(i));
                }

                //build answer string
                StringBuilder answerStringBuilder = new StringBuilder();
                ArrayList<String> answerStrList = new ArrayList<>();
                for (int i=0;i<answerList.getListModel().size();++i){
                    answerStrList.add(answerList.getListModel().get(i));
                }
                Object[] arrayIndexList = answerStrList.stream().map(s -> {
                    for (int i=0;i<choiceList.getListModel().size();++i){
                        if (choiceList.getListModel().get(i).equals(s)){
                            return i;
                        }
                    }
                    return -1;
                }).toArray();
                for (int i=0;i<arrayIndexList.length;++i){
                    answerStringBuilder.append(arrayIndexList[i]);
                    if (i!=arrayIndexList.length-1) answerStringBuilder.append(" ");
                }
                question.setAnswer(answerStringBuilder.toString());

                RankQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
        setMinimumSize(new Dimension(400,400));
        setTitle("Rank Question");
        pack();
        QuestionFactory factory = new QuestionFactory();
        setQuestion(factory.createQuestion(QuestionFactory.QuestionType.RANK));
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        this.question= (RankQuestion)questionToShow;
        promptTextField.setText(this.question.getPrompt());
        this.promptTextField.setText(this.question.getPrompt());
        java.util.List<String> items = this.question.getItem();
        for (String s: items){
            choiceList.getListModel().addElement(s);
        }
        if (isHasAnswer()){
            if(question.getAnswer() == null){
                for (String s: items){
                    answerList.getListModel().addElement(s);
                }
            }else{
                RankAnswer answer = (RankAnswer) question.getAnswer();
                Scanner sin = new Scanner(answer.getAnswer());
                while (sin.hasNext()) {
                    answerList.getListModel().addElement(sin.next());
                }
            }
            question.setScore(this.getScoreValue());
        }
    }
}
