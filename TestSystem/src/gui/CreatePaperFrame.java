package gui;

import gui.listadapter.QuestionAdapter;
import question.ChoiceQuestion;
import question.DecideQuestion;
import question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class CreatePaperFrame extends JFrame {
    private JList<QuestionAdapter> questionList;
    private DefaultListModel<QuestionAdapter> questionListModel;
    private JPopupMenu buildAddQuestionMenu(){
        JPopupMenu addQuestionMenu = new JPopupMenu("Add Question");
        JMenuItem decideQuestionBtn = new JMenuItem("Decide Question");
        JMenuItem choiceQuestionBtn = new JMenuItem("Choice Question");
        JMenuItem rankQuestionBtn = new JMenuItem("Rank Question");
        JMenuItem shortEssayQuestionBtn =new JMenuItem("Short Essay Question");
        JMenuItem essayQuestionBtn = new JMenuItem("Essay Question");
        JMenuItem mapQuestionBtn = new JMenuItem("Map Question");
        addQuestionMenu.add(decideQuestionBtn);
        addQuestionMenu.add(choiceQuestionBtn);
        addQuestionMenu.add(rankQuestionBtn);
        addQuestionMenu.add(shortEssayQuestionBtn);
        addQuestionMenu.add(essayQuestionBtn);
        addQuestionMenu.add(mapQuestionBtn);
        return addQuestionMenu;
    }
    private ActionListener createMoveBtnActionListener(boolean isUp){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionList.isSelectionEmpty()) return ;
                int selectIndex = questionList.getSelectedIndex();
                QuestionAdapter item = questionListModel.getElementAt(selectIndex);
                int targetIndex = selectIndex;
                if (isUp && selectIndex!=0) targetIndex--;
                if ((!isUp) && (selectIndex != questionListModel.size()-1)) targetIndex++;
                questionListModel.remove(selectIndex);
                questionListModel.add(targetIndex,item);
                questionList.setSelectedIndex(targetIndex);
            }
        };
    }
    private JComponent createTopBtn(){
        JPanel ret = new JPanel(new GridLayout(1,5));
        JButton addBtn = new JButton("Add");
        JButton modifyBtn = new JButton("Modify");
        JButton moveUpBtn = new JButton("Up");
        JButton moveDownBtn = new JButton("Down");
        JButton removeBtn = new JButton("Remove");
        ret.add(addBtn);
        ret.add(modifyBtn);
        ret.add(moveUpBtn);
        ret.add(moveDownBtn);
        moveUpBtn.addActionListener(createMoveBtnActionListener(true));
        moveDownBtn.addActionListener(createMoveBtnActionListener(false));
        ret.add(removeBtn);
        JPopupMenu addQuestionMenu = buildAddQuestionMenu();
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestionMenu.setInvoker(addBtn);
                Point point = addBtn.getLocationOnScreen();
                point.setLocation(point.getX(), point.getY() + addBtn.getHeight());
                addQuestionMenu.setLocation(point);
                addQuestionMenu.setVisible(true);
            }
        });
        return ret;
    }
    private JComponent createBottomBtn(){
        JPanel ret= new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new GridLayout(1,2));
        ret.add(rightPanel,BorderLayout.EAST);
        JButton cancelBtn = new JButton("Cancel");
        JButton finishBtn =new JButton("Finish");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreatePaperFrame.this.dispatchEvent(new WindowEvent(CreatePaperFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        rightPanel.add(cancelBtn);
        rightPanel.add(finishBtn);
        return ret;
    }
    public CreatePaperFrame(){
        setLayout(new BorderLayout());
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        add(questionList, BorderLayout.CENTER);
        add(createTopBtn(), BorderLayout.NORTH);
        add(createBottomBtn(),BorderLayout.SOUTH);
        pack();
        setMinimumSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        setTitle("Create Paper");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
