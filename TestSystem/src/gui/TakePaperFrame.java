package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class TakePaperFrame extends JFrame {
    private JButton nextQuestionBtn;
    private JButton giveUpBtn;
    private JButton navPreviousBtn;
    private JButton navNextBtn;
    private JLabel titleCountTips;

    private JPanel createTopPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        navPreviousBtn = new JButton("<-");
        navNextBtn = new JButton("->");
        titleCountTips = new JLabel("xx/xx");
        add(navPreviousBtn,BorderLayout.WEST);
        add(navNextBtn, BorderLayout.EAST);
        add(titleCountTips, BorderLayout.CENTER);
        return ret;
    }

    private JPanel createBottomPanel(){
        JPanel ret= new JPanel(new BorderLayout());
        JPanel btnPanel =new JPanel(new GridLayout(1,2));
        nextQuestionBtn = new JButton("Next Question");
        giveUpBtn = new JButton("Give Up");
        btnPanel.add(giveUpBtn);
        btnPanel.add(nextQuestionBtn);
        ret.add(btnPanel,BorderLayout.EAST);
        return ret;
    }
    public TakePaperFrame(){
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(),BorderLayout.SOUTH);
        //TODO: add question shower
        pack();
        setTitle("Take Paper");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

}
