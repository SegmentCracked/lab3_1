package gui.questionshower;

import Paper.Page;
import Question.Question;
import answer.Answer;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mengxiao Lin on 2016/6/1.
 */
public class CoverShower extends QuestionShower {
    private JTextArea coverContent;
    private JTextField nameTextField;
    public CoverShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new BorderLayout());
        coverContent = new JTextArea();
        add(coverContent,BorderLayout.CENTER);
        JPanel inputPanel =new JPanel(new GridLayout(2,2));
        inputPanel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        inputPanel.add(nameTextField);
        inputPanel.add(new JLabel("Time:"));
        Date currentDate = Calendar.getInstance().getTime();
        inputPanel.add(new JLabel(currentDate.toString()));
        add(inputPanel, BorderLayout.SOUTH);
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
        return nameTextField.getText().length()!=0;
    }

    @Override
    public String getAnswer() {
        return nameTextField.getText();
    }
}
