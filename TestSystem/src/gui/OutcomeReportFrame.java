package gui;

import Control.Control;
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
public class OutcomeReportFrame extends JFrame {
    private JTextArea reportTextArea;
    private String reportText(){
        return Control.getInstance().getOutcome();
    }
    public OutcomeReportFrame()  {
        reportTextArea = new JTextArea();
        reportTextArea.setText(reportText());
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
                OutcomeReportFrame.this.setVisible(false);
            }
        });
        setMinimumSize(new Dimension(400,300));
        add(reportTextPane,BorderLayout.CENTER);
        add(closeBtn, BorderLayout.SOUTH);
        setTitle("Outcome : "+ Control.getInstance().getPage().getPageName());
        pack();
        setLocationRelativeTo(null);
    }
}
