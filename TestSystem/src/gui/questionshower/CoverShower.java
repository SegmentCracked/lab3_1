package gui.questionshower;

import Paper.Page;
import Question.Question;
import answer.Answer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/1.
 */
public class CoverShower extends QuestionShower {
    private JTextArea coverContent;
    public CoverShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new GridLayout(1,1));
        coverContent = new JTextArea();
        add(coverContent);
    }
    public void parseMetaInformation(Page page){
        StringBuilder content = new StringBuilder();
        content.append("Page Name: ");
        content.append(page.getPageName());
        content.append("\nQuestion Count: ");
        content.append(page.getQuestionSize());
        coverContent.setText(content.toString());
    }

    @Override
    public void clearInput() {

    }

    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public String getAnswer() {
        return null;
    }
}
