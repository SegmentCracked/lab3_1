package gui;

import gui.listadapter.QuestionAdapter;
import question.ChoiceQuestion;
import question.DecideQuestion;
import question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class CreatePaperFrame extends JFrame {
    private JList<QuestionAdapter> questionList;
    private DefaultListModel<QuestionAdapter> questionListModel;
    private JPanel createBottomBtn(){
        JPanel ret = new JPanel(new GridLayout(1,5));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton moveUpBtn = new JButton("Up");
        JButton moveDownBtn = new JButton("Down");
        JButton removeBtn = new JButton("Remove");
        ret.add(addBtn);
        ret.add(editBtn);
        ret.add(moveUpBtn);
        ret.add(moveDownBtn);
        ret.add(removeBtn);
        return ret;
    }
    public CreatePaperFrame(){
        setLayout(new BorderLayout());
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        add(questionList, BorderLayout.CENTER);
        add(createBottomBtn(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setTitle("Create Paper");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
