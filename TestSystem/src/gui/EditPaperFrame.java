package gui;

import Control.Control;
import gui.questionedit.*;
import Paper.Page;
import Question.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class EditPaperFrame extends JFrame {
    private JList<QuestionAdapter> questionList;
    private DefaultListModel<QuestionAdapter> questionListModel;
    private JTextField titleTextField;
    private JComboBox<String> typeComboBox;
    private JSpinner timeLimitSpinner;
    private boolean isNewPage;
    private int getPageType(){
        return typeComboBox.getSelectedIndex();
    }
    private ActionListener questionBtnListenerFactory(Class frameClass){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionFrame d = null;
                try {
                    d = (QuestionFrame)frameClass.getConstructor(boolean.class).newInstance(getPageType() == 1);
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
        JMenuItem essayQuestionBtn = new JMenuItem("Essay Question");
        JMenuItem mapQuestionBtn = new JMenuItem("Map Question");
        addQuestionMenu.add(decideQuestionBtn);
        decideQuestionBtn.addActionListener(questionBtnListenerFactory(DecideQuestionFrame.class));
        addQuestionMenu.add(choiceQuestionBtn);
        choiceQuestionBtn.addActionListener(questionBtnListenerFactory(ChoiceQuestionFrame.class));
        addQuestionMenu.add(rankQuestionBtn);
        rankQuestionBtn.addActionListener(questionBtnListenerFactory(RankQuestionFrame.class));
        addQuestionMenu.add(essayQuestionBtn);
        essayQuestionBtn.addActionListener(questionBtnListenerFactory(EssayQuestionFrame.class));
        addQuestionMenu.add(mapQuestionBtn);
        mapQuestionBtn.addActionListener(questionBtnListenerFactory(MapQuestionFrame.class));
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
                QuestionFrame f = QuestionFrame.createFrameByQuestion(question, getPageType() ==1 );
                f.setLocationRelativeTo(EditPaperFrame.this);
                f.setVisible(true);
                questionList.setModel(questionListModel);
            }
        });
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionList.isSelectionEmpty()) return ;
                int selectedIndex = questionList.getSelectedIndex();
                questionListModel.remove(selectedIndex);
            }
        });
        return ret;
    }
    private void closeFrame(){
        this.dispatchEvent(new WindowEvent(EditPaperFrame.this, WindowEvent.WINDOW_CLOSING));
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
                closeFrame();
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control control =Control.getInstance();

                if (isNewPage) {
                    control.createPage(getPageType());
                    control.setPageName(titleTextField.getText());
                }
                else control.getPage().clearPage();
                control.getPage().setTimeLimit((int)timeLimitSpinner.getValue());
                for (int i=0;i<questionListModel.size();++i){
                    Question question = questionListModel.get(i).getQuestion();
                    control.addQuestionToPage(question);
                }
                control.save();
                JOptionPane.showConfirmDialog(EditPaperFrame.this,
                        "Paper saved!",
                        "Edit Paper",
                        JOptionPane.DEFAULT_OPTION);
                closeFrame();
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
    private JPanel createPageTypeBox(){
        typeComboBox = new JComboBox<>();
        typeComboBox.addItem("Survey");
        typeComboBox.addItem("Test");
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("Type:"), BorderLayout.WEST);
        ret.add(typeComboBox);
        return ret;
    }
    private JPanel createTimeLimitBox(){
        JPanel ret = new JPanel(new BorderLayout());
        timeLimitSpinner = new JSpinner();
        JSpinner.NumberEditor timeLimitEditor= new JSpinner.NumberEditor(timeLimitSpinner);
        timeLimitSpinner.setEditor(timeLimitEditor);
        timeLimitEditor.getModel().setMinimum(0);
        ret.add(new JLabel("Time Limit(min): "),BorderLayout.WEST);
        ret.add(timeLimitSpinner, BorderLayout.CENTER);
        ret.add(new JLabel("(0 is unlimited!)"),BorderLayout.EAST);
        return ret;
    }
    /**
     * parse page from control
     */
    public void parsePage(){
        Control control = Control.getInstance();
        Page page = control.getPage();
        if (page==null){
            throw new NullPointerException();
        }
        this.titleTextField.setText(page.getPageName());
        this.titleTextField.setEditable(false);
        this.typeComboBox.setSelectedIndex(page.getTypeId());
        this.typeComboBox.setEditable(false);
        this.typeComboBox.setEnabled(false);
        this.typeComboBox.setForeground(Color.BLACK);
        for (int i=0;i<page.getQuestionSize();++i){
            questionListModel.addElement(new QuestionAdapter(page.getQuestion(i)));
        }
        isNewPage = false;
        timeLimitSpinner.setValue(page.getTimeLimit());
    }

    public EditPaperFrame(){
        setLayout(new BorderLayout());
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        add(questionList, BorderLayout.CENTER);

        JPanel topPanel =new JPanel(new GridLayout(4,1));
        topPanel.add(createPageTitleBox());
        topPanel.add(createPageTypeBox());
        topPanel.add(createTimeLimitBox());
        topPanel.add(createTopBtn());
        add(topPanel, BorderLayout.NORTH);
        add(createBottomBtn(),BorderLayout.SOUTH);
        pack();
        setMinimumSize(new Dimension(400,300));
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle("Edit Paper");
        isNewPage = true;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
