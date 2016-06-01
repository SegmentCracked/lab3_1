package gui;

import Control.Control;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        int index;
        int type;
        final static int SURVEY = 0;
        final static int TEST = 1;

        public PageAdapter(String name, int index, int type) {
            this.name = name;
            this.index = index;
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

    private void loadPaperList(){
        Control control = Control.getInstance();
        List<String> pageList = control.getPageName(0);
        paperListModel.clear();
        realPageList.clear();
        for (int i=0;i<pageList.size();++i){
            String s = pageList.get(i);
            paperListModel.addElement(s);
            realPageList.add(new PageAdapter(s,i, 0));
        }
        pageList = control.getPageName(1);
        for(int i=0;i<pageList.size();++i){
            String s= pageList.get(i);
            paperListModel.addElement(s);
            realPageList.add(new PageAdapter(s, i, 1));
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
        editPaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paperList.isSelectionEmpty()) return ;
                PageAdapter pageAdapter = realPageList.get(paperList.getSelectedIndex());
                Control control = Control.getInstance();
                control.loadPage(pageAdapter.index, pageAdapter.type);
                EditPaperFrame f = new EditPaperFrame();
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPaperList();
                    }
                });
                f.parsePage();
                f.setVisible(true);
            }
        });
        takePaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paperList.isSelectionEmpty()) return ;
                PageAdapter pageAdapter = realPageList.get(paperList.getSelectedIndex());
                Control control = Control.getInstance();
                control.loadPage(pageAdapter.index, pageAdapter.type);
                TakePaperFrame f =new TakePaperFrame();
                f.setVisible(true);
            }
        });
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
                if (selectedIndex == -1) return ;
                tipsLabel.setText(realPageList.get(selectedIndex).toString());
            }
        });
        setMinimumSize(new Dimension(300,300));
        pack();
        setTitle("Load Paper");
        setLocationRelativeTo(null);
    }
}
