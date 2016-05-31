package gui;

import gui.questionedit.*;
import Paper.Page;
import Question.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class EditPaperFrame extends JFrame {
    private JList<QuestionAdapter> questionList;
    private DefaultListModel<QuestionAdapter> questionListModel;
    private JTextField titleTextField;
    private ActionListener questionBtnListenerFactory(Class frameClass, boolean hasAnswer){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionFrame d = null;
                try {
                    d = (QuestionFrame)frameClass.getConstructor(boolean.class).newInstance(hasAnswer);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                    e1.printStackTrace();
                    return;
                }
                d.setLocationRelativeTo(EditPaperFrame.this);
                d.setVisible(true);
                if (d.getQuestion() != null){
                    questionListModel.addElement(new QuestionAdapter(d.getQuestion()));
                }
            }
        };
    }
    private JPopupMenu buildAddQuestionMenu(){
        JPopupMenu addQuestionMenu = new JPopupMenu("Add Question");
        JMenuItem decideQuestionBtn = new JMenuItem("Decide Question");
        JMenuItem choiceQuestionBtn = new JMenuItem("Choice Question");
        JMenuItem rankQuestionBtn = new JMenuItem("Rank Question");
        JMenuItem shortEssayQuestionBtn =new JMenuItem("Short Essay Question");
        JMenuItem essayQuestionBtn = new JMenuItem("Essay Question");
        JMenuItem mapQuestionBtn = new JMenuItem("Map Question");
        addQuestionMenu.add(decideQuestionBtn);
        decideQuestionBtn.addActionListener(questionBtnListenerFactory(DecideQuestionFrame.class, false));
        addQuestionMenu.add(choiceQuestionBtn);
        choiceQuestionBtn.addActionListener(questionBtnListenerFactory(ChoiceQuestionFrame.class, false));
        addQuestionMenu.add(rankQuestionBtn);
        rankQuestionBtn.addActionListener(questionBtnListenerFactory(RankQuestionFrame.class, false));
        addQuestionMenu.add(shortEssayQuestionBtn);
        addQuestionMenu.add(essayQuestionBtn);
        addQuestionMenu.add(mapQuestionBtn);
        mapQuestionBtn.addActionListener(questionBtnListenerFactory(MapQuestionFrame.class, false));
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
        modifyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionList.isSelectionEmpty()) return ;
                int selectedIndex = questionList.getSelectedIndex();
                Question question = questionListModel.get(selectedIndex).getQuestion();
                QuestionFrame f = QuestionFrame.createFrameByQuestion(question, false);
                f.setLocationRelativeTo(EditPaperFrame.this);
                f.setVisible(true);
                questionList.setModel(questionListModel);
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
                EditPaperFrame.this.dispatchEvent(new WindowEvent(EditPaperFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        rightPanel.add(cancelBtn);
        rightPanel.add(finishBtn);
        return ret;
    }
    private JPanel createPageTitleBox(){
        titleTextField = new JTextField();
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("Title:"), BorderLayout.WEST);
        ret.add(titleTextField,BorderLayout.CENTER);
        return ret;
    }
    public void parsePage(Page page){
        this.titleTextField.setText(page.getPageName());
        for (int i=0;i<page.getQuestionSize();++i){
            questionListModel.addElement(new QuestionAdapter(page.getQuestion(i)));
        }
    }

    public EditPaperFrame(){
        setLayout(new BorderLayout());
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        add(questionList, BorderLayout.CENTER);

        JPanel topPanel =new JPanel(new GridLayout(2,1));
        topPanel.add(createPageTitleBox());
        topPanel.add(createTopBtn());
        add(topPanel, BorderLayout.NORTH);
        add(createBottomBtn(),BorderLayout.SOUTH);
        pack();
        setMinimumSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        setTitle("Edit Paper");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
