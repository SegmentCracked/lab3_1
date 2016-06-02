package gui;

import Control.Control;
import Paper.Page;
import Question.Question;
import gui.questionshower.CoverShower;
import gui.questionshower.QuestionShower;
import gui.questionshower.QuestionShowerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class TakePaperFrame extends JFrame {
    private JButton nextQuestionBtn;
    private JButton giveUpBtn;
    private JButton navPreviousBtn;
    private JButton navNextBtn;
    private JLabel titleCountTips;
    private QuestionShower currentQuestionShower;

    private JPanel createTopPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        navPreviousBtn = new JButton("<-");
        navNextBtn = new JButton("->");
        titleCountTips = new JLabel("xx/xx");
        titleCountTips.setVerticalAlignment(JLabel.CENTER);
        titleCountTips.setHorizontalAlignment(JLabel.CENTER);
        ret.add(navPreviousBtn,BorderLayout.WEST);
        ret.add(navNextBtn, BorderLayout.EAST);
        ret.add(titleCountTips, BorderLayout.CENTER);
        return ret;
    }

    private JPanel createBottomPanel(){
        JPanel ret= new JPanel(new BorderLayout());
        JPanel btnPanel =new JPanel(new GridLayout(1,2));
        nextQuestionBtn = new JButton("Next Question");
        giveUpBtn = new JButton("Give Up");
        btnPanel.add(giveUpBtn);
        giveUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TakePaperFrame.this.setVisible(false);
            }
        });
        nextQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentQuestionShower.isFilled()){
                    JOptionPane.showConfirmDialog(TakePaperFrame.this,
                            "Please fill this question",
                            "Take Paper",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                Control control = Control.getInstance();
                QuestionShowerFactory questionShowerFactory = new QuestionShowerFactory();
                if (! (currentQuestionShower instanceof CoverShower)){
                    control.answerQuestion(currentQuestionShower.getAnswer().getAnswer());
                }
                if (control.hasNextQuestion()){
                    Question question = control.nextQuestion();
                    TakePaperFrame.this.remove(currentQuestionShower);
                    currentQuestionShower = questionShowerFactory.createQuestionShowerByQuestion(question);
                    add(currentQuestionShower, BorderLayout.CENTER);
                }
                TakePaperFrame.this.revalidate();
            }
        });
        btnPanel.add(nextQuestionBtn);
        ret.add(btnPanel,BorderLayout.EAST);
        return ret;
    }
    public TakePaperFrame(){
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(),BorderLayout.SOUTH);

        CoverShower coverShower = new CoverShower(null);
        coverShower.parseMetaInformation(Control.getInstance().getPage());
        add(coverShower, BorderLayout.CENTER);
        currentQuestionShower=coverShower;
        setMinimumSize(new Dimension(400,300));
        pack();
        setTitle("Take Paper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

}
