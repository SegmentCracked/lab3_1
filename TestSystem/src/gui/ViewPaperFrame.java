package gui;

import Control.Control;
import Control.QuestionControl;
import Paper.Page;
import Paper.Record;
import Paper.Test;
import gui.util.PageAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class ViewPaperFrame extends JFrame{
    private JList<String> recordList;
    private DefaultListModel<String> recordListModel;
    private void loadRecordToList(){
        Control control= Control.getInstance();
        control.loadRecord();
        control.getRecordName().forEach(s->{
            recordListModel.addElement(s);
        });
    }
    private JPanel createRecordListPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        recordList = new JList<>();
        recordListModel = new DefaultListModel<>();
        recordList.setModel(recordListModel);
        ret.add(new JLabel("Records:"),BorderLayout.NORTH);
        ret.add(recordList, BorderLayout.CENTER);
        loadRecordToList();
        return ret;
    }
    private JPanel createPageInfoPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("Information:"),BorderLayout.NORTH);
        JTextArea infoTextArea = new JTextArea();
        final Page pageToShow = Control.getInstance().getPage();
        PageAdapter pageAdapter= new PageAdapter(0, pageToShow);
        infoTextArea.setText(pageAdapter.toString());
        infoTextArea.setEditable(false);
        ret.add(infoTextArea, BorderLayout.CENTER);
        return ret;
    }
    private JPanel createBottomBtnPanel(){
        JPanel ret = new JPanel(new GridLayout(1,2));
        JButton gradeTestBtn = new JButton("Grade Test");
        if (Control.getInstance().getPage().getTypeId() == 0){
            gradeTestBtn.setEnabled(false);
        }
        gradeTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordList.isSelectionEmpty()) return ;
                Record recordToGrade = Control.getInstance().getRecordByName(recordListModel.get(recordList.getSelectedIndex()));
                RecordReportFrame f = new RecordReportFrame((Test)Control.getInstance().getPage(),recordToGrade);
                f.setVisible(true);
            }
        });
        JButton outcomeBtn = new JButton("Outcome");
        outcomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OutcomeReportFrame outcomeReportFrame =new OutcomeReportFrame();
                outcomeReportFrame.setVisible(true);
            }
        });
        ret.add(gradeTestBtn);
        ret.add(outcomeBtn);
        return ret;
    }
    public ViewPaperFrame(){
        setLayout(new BorderLayout());
        add(createPageInfoPanel(), BorderLayout.NORTH);
        add(createRecordListPanel(), BorderLayout.CENTER);
        add(createBottomBtnPanel(), BorderLayout.SOUTH);
        setMinimumSize(new Dimension(500,400));
        pack();
        setTitle("View Paper");
        setLocationRelativeTo(null);
    }
}
