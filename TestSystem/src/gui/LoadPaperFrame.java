package gui;

import Control.Control;
import gui.util.PageAdapter;

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
    private JButton viewPaperBtn;
    private JTextArea tipsLabel;

    private void loadPaperList(){
        Control control = Control.getInstance();
        List<String> pageList = control.getPageName(0);
        paperListModel.clear();
        realPageList.clear();
        for (int i=0;i<pageList.size();++i){
            String s = pageList.get(i);
            paperListModel.addElement(s);
            control.loadPage(i,0);
            realPageList.add(new PageAdapter(i, control.getPage()));
        }
        pageList = control.getPageName(1);
        for(int i=0;i<pageList.size();++i){
            String s= pageList.get(i);
            paperListModel.addElement(s);
            control.loadPage(i,1);
            realPageList.add(new PageAdapter(i, control.getPage()));
        }
    }

    private JPanel createRightPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        tipsLabel = new JTextArea();
        tipsLabel.setEditable(false);
        tipsLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        JPanel btnPanel = new JPanel(new GridLayout(3,1));
        editPaperBtn =new JButton("Edit it");
        btnPanel.add(editPaperBtn);
        takePaperBtn = new JButton("Take it");
        btnPanel.add(takePaperBtn);
        viewPaperBtn = new JButton("View it");
        btnPanel.add(viewPaperBtn);
        editPaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paperList.isSelectionEmpty()) return ;
                PageAdapter pageAdapter = realPageList.get(paperList.getSelectedIndex());
                Control control = Control.getInstance();
                control.loadPage(pageAdapter.getIndex(), pageAdapter.getType());
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
                control.loadPage(pageAdapter.getIndex(), pageAdapter.getType());
                TakePaperFrame f =new TakePaperFrame();
                f.setVisible(true);
            }
        });
        viewPaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paperList.isSelectionEmpty()) return;
                PageAdapter pageAdapter = realPageList.get(paperList.getSelectedIndex());
                Control control = Control.getInstance();
                control.loadPage(pageAdapter.getIndex(), pageAdapter.getType());
                ViewPaperFrame f =new ViewPaperFrame();
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
        setMinimumSize(new Dimension(500,400));
        pack();
        setTitle("Load Paper");
        setLocationRelativeTo(null);
    }
}
