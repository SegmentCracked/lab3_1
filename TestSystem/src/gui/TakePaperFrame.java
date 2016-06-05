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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class TakePaperFrame extends JFrame {
    private JButton nextQuestionBtn;
    private JButton giveUpBtn;
    private JLabel titleCountTips;
    private JTextField timeLimitField;
    private Thread timeCounterThread;
    private QuestionShower currentQuestionShower;
    private int currentQuestionPos = 0;
    private long startTimeStamp;
    private int timeLimit;
    private boolean timesUp;
    private boolean isDoing;
    public String getTextForCountTips(){
        Control control = Control.getInstance();
        int questionSize = control.getPage().getQuestionSize();
        return currentQuestionPos+"/"+questionSize;
    }
    private JPanel createTopPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        titleCountTips = new JLabel(getTextForCountTips());
        titleCountTips.setVerticalAlignment(JLabel.CENTER);
        titleCountTips.setHorizontalAlignment(JLabel.CENTER);
        ret.add(titleCountTips, BorderLayout.CENTER);
        return ret;
    }
    private void closeFrame(){
        isDoing =false;
        if (timeCounterThread!=null) {
            try {
                timeCounterThread.interrupt();
                timeCounterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TakePaperFrame.this.setVisible(false);
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
                closeFrame();
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
                if (currentQuestionShower instanceof CoverShower){
                    control.setRecordName(currentQuestionShower.getAnswer());
                    if (timeCounterThread!=null) timeCounterThread.start();
                }else{
                    control.answerQuestion(currentQuestionShower.getAnswer());
                }
                if (control.hasNextQuestion()){
                    Question question = control.nextQuestion();
                    currentQuestionPos++;
                    TakePaperFrame.this.remove(currentQuestionShower);
                    currentQuestionShower = questionShowerFactory.createQuestionShowerByQuestion(question);
                    add(currentQuestionShower, BorderLayout.CENTER);
                    TakePaperFrame.this.revalidate();
                    titleCountTips.setText(getTextForCountTips());
                }else{
                    control.saveAnswer();
                    JOptionPane.showConfirmDialog(TakePaperFrame.this, "You finished all the questions!","TestSystem",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    closeFrame();
                }
            }
        });
        btnPanel.add(nextQuestionBtn);
        ret.add(btnPanel,BorderLayout.EAST);
        if (timeLimit!=0) {
            JPanel timeLimitBox = new JPanel(new BorderLayout());
            timeLimitBox.add(new JLabel("Time left: "),BorderLayout.WEST);
            timeLimitField = new JTextField();
            timeLimitField.setEditable(false);
            timeLimitBox.add(timeLimitField);
            timeLimitField.setText((timeLimit/60/1000)+"m              ");
            ret.add(timeLimitBox,BorderLayout.WEST);
        }
        return ret;
    }
    public TakePaperFrame(){
        setLayout(new BorderLayout());
        timeLimit = Control.getInstance().getPage().getTimeLimit()*60*1000;
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
        isDoing = true;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (timeLimit == 0) return;
                timeCounterThread = new Thread(() -> {
                    startTimeStamp = System.currentTimeMillis();
                    timesUp =false;
                    try {
                        while (true) {
                            Thread.sleep(500);
                            if (!isDoing) return ;
                            long currentTimeStamp = System.currentTimeMillis();
                            final int timeUsed = (int) (currentTimeStamp - startTimeStamp);
                            if (timeUsed > timeLimit) {
                                Control.getInstance().saveAnswer();
                                JOptionPane.showConfirmDialog(TakePaperFrame.this, "Time's up!", "Take Paper", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                timesUp = true;
                                break;
                            }
                            int timeLeft = timeLimit - timeUsed;
                            String text = "";
                            timeLeft /= 1000;
                            if (timeLeft >= 60) {
                                text += timeLeft / 60;
                                text += "m";
                            }
                            timeLeft %= 60;
                            if (timeLeft != 0) {
                                text += timeLeft;
                                text += "sec";
                            }
                            timeLimitField.setText(text);
                        }
                        if (timesUp){
                            dispatchEvent(new WindowEvent(TakePaperFrame.this, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                    catch (InterruptedException e1) {
                        return;
                    }
                });
            }

            //since the frame could not be close, this event is used to get information from timer
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (timesUp) closeFrame();
            }
        });
    }

}
