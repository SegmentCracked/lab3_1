package gui.questionframe;

import question.ChoiceQuestion;
import question.Question;

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
    private JList<String> itemList;
    private DefaultListModel<String> itemListModel;
    private JPopupMenu createItemListMenu(){
        JPopupMenu itemListMenu=new JPopupMenu();
        JMenuItem addItemBtn = new JMenuItem("Add choice");
        JMenuItem removeItemBtn = new JMenuItem("Remove choice");
        itemListMenu.add(addItemBtn);
        itemListMenu.add(removeItemBtn);

        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(ChoiceQuestionFrame.this,"The title of the choice:","Add choice", JOptionPane.QUESTION_MESSAGE).trim();
                if (s!=null) itemListModel.addElement(s);
            }
        });
        removeItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemList.isSelectionEmpty()) return ;
                int selectedIndex = itemList.getSelectedIndex();
                itemListModel.remove(selectedIndex);
            }
        });
        return itemListMenu;
    }
    private JPanel createMainPanel(){
        JPanel ret =new JPanel(new BorderLayout());

        JPanel topPanel =new JPanel(new GridLayout(3,1));
        topPanel.add(new JLabel("Prompt:"));
        promptTextField=new JTextField();
        topPanel.add(promptTextField);
        topPanel.add(new JLabel("Choices:"));
        ret.add(topPanel,BorderLayout.NORTH);

        itemListModel=new DefaultListModel<>();
        itemList=new JList<>(itemListModel);
        itemList.setComponentPopupMenu(createItemListMenu());
        ret.add(itemList, BorderLayout.CENTER);
        return ret;
    }
    public ChoiceQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        question = new ChoiceQuestion();
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
                for (int i=0;i<itemListModel.size();++i){
                    question.addItem(itemListModel.get(i));
                }
                ChoiceQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
        setMinimumSize(new Dimension(300,200));
        setTitle("Choice Question");
        pack();
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
            itemListModel.addElement(s);
        }
    }
}
