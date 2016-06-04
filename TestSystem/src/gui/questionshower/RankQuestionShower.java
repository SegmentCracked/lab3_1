package gui.questionshower;

import Question.Question;
import Question.RankQuestion;
import answer.Answer;
import gui.questionedit.ItemList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class RankQuestionShower extends QuestionShower {
    private JLabel promptLabel;
    private ItemList answerList;
    private RankQuestion question;
    private void reloadItems(){
        answerList.getListModel().clear();
        question.getItem().forEach(e->answerList.getListModel().addElement(e));
    }
    public RankQuestionShower(Question questionToShow) {
        super(questionToShow);
        this.question = (RankQuestion) questionToShow;
        setLayout(new BorderLayout());

        this.promptLabel = new JLabel(questionToShow.getPrompt());
        answerList = new ItemList(this);
        answerList.hideRemoveMenuItem();
        answerList.hideAddMenuItem();
        reloadItems();
        add(promptLabel, BorderLayout.NORTH);
        add(answerList.getList(), BorderLayout.CENTER);
    }

    @Override
    public void clearInput() {
        reloadItems();
    }

    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public String getAnswer() {
        StringBuilder answerStringBuilder = new StringBuilder();
        ArrayList<String> answerStrList = new ArrayList<>();
        for (int i=0;i<answerList.getListModel().size();++i){
            answerStrList.add(answerList.getListModel().get(i));
        }
        Object[] arrayIndexList = answerStrList.stream().map(s -> {
            for (int i=0;i<question.getItem().size();++i){
                if (question.getItem().get(i).equals(s)){
                    return i;
                }
            }
            return -1;
        }).toArray();
        for (int i=0;i<arrayIndexList.length;++i){
            answerStringBuilder.append(arrayIndexList[i]);
            if (i!=arrayIndexList.length-1) answerStringBuilder.append(" ");
        }
        return answerStringBuilder.toString();
    }
}
