package gui;

import control.Control;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class LoadPaperFrame extends JFrame {
    private JList<String> paperList;
    private DefaultListModel<String> paperListModel;
    private List<PageAdapter> realPageList;
    private JButton editPaperBtn;
    private JButton takePaperBtn;
    private JTextArea tipsLabel;

    class PageAdapter{
        String name;
        int type;
        final static int SURVEY = 0;
        final static int TEST = 1;

        public PageAdapter(String name, int type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            String ret = "Page Name: "+name +"\nType: ";
            if (type == SURVEY) ret+="Survey";
            if (type == TEST) ret+="Test";
            return ret;
        }
    }

    /*TODO: rewrite loadPaperList() after the Control is refactored */
    private void loadPaperList(){
        Control control = new Control();
        List<String> pageList = control.getPageName(0);
        for (String s: pageList){
            paperListModel.addElement(s);
            realPageList.add(new PageAdapter(s, 0));
        }
        pageList = control.getPageName(1);
        for(String s: pageList){
            paperListModel.addElement(s);
            realPageList.add(new PageAdapter(s, 0));
        }
    }

    private JPanel createRightPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        tipsLabel = new JTextArea();
        tipsLabel.setEditable(false);
        tipsLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        JPanel btnPanel = new JPanel(new GridLayout(2,1));
        editPaperBtn =new JButton("Edit");
        btnPanel.add(editPaperBtn);
        takePaperBtn = new JButton("Take it");
        btnPanel.add(takePaperBtn);
        ret.add(tipsLabel, BorderLayout.NORTH);
        ret.add(btnPanel,BorderLayout.SOUTH);
        return ret;
    }

    public LoadPaperFrame(){
        paperList = new JList<>();
        paperListModel = new DefaultListModel<>();
        paperList.setModel(paperListModel);
        realPageList =new ArrayList<>();
        loadPaperList();

        setLayout(new GridLayout(1,2));
        add(paperList);
        add(createRightPanel());
        paperList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paperList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = paperList.getSelectedIndex();
                tipsLabel.setText(realPageList.get(selectedIndex).toString());
            }
        });
        setMinimumSize(new Dimension(300,300));
        pack();
        setTitle("Load Paper");
        setLocationRelativeTo(null);
    }
}
