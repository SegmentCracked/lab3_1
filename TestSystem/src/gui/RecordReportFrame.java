package gui;

import Paper.Page;
import Paper.Record;
import Paper.Test;
import answer.Answer;
import answer.TextAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class RecordReportFrame extends JFrame {
    private JTextArea reportTextArea;
    private String reportText(Test pageToShow, Record recordToShow){
        StringBuilder reportTextBuilder =new StringBuilder();
        reportTextBuilder.append("Total score: ").append(pageToShow.getTotalScore()).append("\n");
        reportTextBuilder.append("Total score without text problem: ").append(pageToShow.getScoreWithoutTextAnswer()).append("\n");
        reportTextBuilder.append("Tester's score without text problem: ").append(recordToShow.getScore()).append("\n");
        reportTextBuilder.append("\n");
        Iterator<Answer> answerIterator = recordToShow.iterator();
        int index = 0;
        while (answerIterator.hasNext()){
            Answer answer = answerIterator.next();
            ++index;
            if(answer instanceof TextAnswer){
                reportTextBuilder.append("======");
                reportTextBuilder.append("Text answer for problem ").append(index).append(":\n");
                reportTextBuilder.append(answer.getAnswer()).append("\n");
            }
        }
        return reportTextBuilder.toString();
    }
    public RecordReportFrame(Test pageToShow, Record recordToShow)  {
        reportTextArea = new JTextArea();
        reportTextArea.setText(reportText(pageToShow,recordToShow));
        reportTextArea.setLineWrap(true);
        reportTextArea.setEditable(false);
        JScrollPane reportTextPane = new JScrollPane(reportTextArea);
        reportTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reportTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setLayout(new BorderLayout());
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecordReportFrame.this.setVisible(false);
            }
        });
        setMinimumSize(new Dimension(400,300));
        add(reportTextPane,BorderLayout.CENTER);
        add(closeBtn, BorderLayout.SOUTH);
        setTitle("Grade The Test: "+recordToShow.getPersonName());
        pack();
        setLocationRelativeTo(null);
    }
}
