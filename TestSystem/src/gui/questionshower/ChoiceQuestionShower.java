package gui.questionshower;

import Question.Question;
import Question.ChoiceQuestion;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class ChoiceQuestionShower extends QuestionShower{
    private JLabel promptLabel;
    private JList<String> choiceList;
    private DefaultListModel<String> choiceListModel;
    private ChoiceQuestion question;
    public ChoiceQuestionShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new BorderLayout());
        this.question = (ChoiceQuestion) questionToShow;
        choiceList = new JList<>();
        choiceListModel = new DefaultListModel<>();
        choiceList.setModel(choiceListModel);
        choiceList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        promptLabel = new JLabel(questionToShow.getPrompt());
        this.question.getItem().forEach(s-> choiceListModel.addElement(s));
        add(promptLabel, BorderLayout.NORTH);
        add(choiceList, BorderLayout.CENTER);
    }

    @Override
    public void clearInput() {
        choiceList.clearSelection();
    }

    @Override
    public boolean isFilled() {
        return !choiceList.isSelectionEmpty();
    }

    @Override
    public String getAnswer() {
        StringBuilder answerStrBuilder = new StringBuilder();
        final int[] selectedIndices = choiceList.getSelectedIndices();
        for (int i=0;i<selectedIndices.length;++i){
            answerStrBuilder.append(selectedIndices[i]);
            answerStrBuilder.append(" ");
        }
        return answerStrBuilder.toString();
    }
}
