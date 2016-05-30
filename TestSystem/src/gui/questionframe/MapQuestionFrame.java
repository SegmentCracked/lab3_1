package gui.questionframe;

import question.MapQuestion;
import question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class MapQuestionFrame extends QuestionFrame{
    private MapQuestion question;
    private JTextField promptTextField;
    private ItemList leftList, rightList;
    public JPanel createMainPanel(){
        JPanel ret = new JPanel(new GridLayout(1,2));
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        leftList=new ItemList(this);
        rightList= new ItemList(this);
        leftPanel.add(new Label("Left:"),BorderLayout.NORTH);
        leftPanel.add(leftList.getList(), BorderLayout.CENTER);
        rightPanel.add(new Label("Right:"),BorderLayout.NORTH);
        rightPanel.add(rightList.getList(),BorderLayout.CENTER);

        ret.add(leftPanel);
        ret.add(rightPanel);
        return ret;
    }
    public MapQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        setLayout(new BorderLayout());

        JPanel topPanel =new JPanel(new GridLayout(2,1));
        topPanel.add(new JLabel("Prompt:"));
        promptTextField =new JTextField();
        topPanel.add(promptTextField);

        add(topPanel,BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createBottomPanel(),BorderLayout.SOUTH);
        setTitle("Map Question");
        setMinimumSize(new Dimension(350,500));
        pack();
        setQuestion(new MapQuestion());
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                MapQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question.setPrompt(promptTextField.getText());
                MapQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        this.question = (MapQuestion) questionToShow;
        promptTextField.setText(questionToShow.getPrompt());
    }
}
