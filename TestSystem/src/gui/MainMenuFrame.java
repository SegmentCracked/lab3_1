package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/5/29.
 */
public class MainMenuFrame extends JFrame {
    private JButton createPaperBtn;
    private JButton loadPaperBtn;
    private JButton quitBtn;

    private void hideFrame(){
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(false);
    }
    private void reshowFrame(){
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel createBtnPanel(){
        JPanel ret = new JPanel();
        ret.setLayout(new GridLayout(3,1));
        createPaperBtn = new JButton("Create Paper ...");
        loadPaperBtn = new JButton("Load Paper ...");
        quitBtn = new JButton("Quit Test System");

        createPaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPaperFrame editPaperFrame = new EditPaperFrame();
                editPaperFrame.setVisible(true);
                MainMenuFrame.this.hideFrame();
                editPaperFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        MainMenuFrame.this.reshowFrame();
                    }
                });
            }
        });

        loadPaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadPaperFrame loadPaperFrame= new LoadPaperFrame();
                loadPaperFrame.setVisible(true);
                MainMenuFrame.this.hideFrame();
                loadPaperFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        MainMenuFrame.this.reshowFrame();
                    }
                });
            }
        });
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ret.add(createPaperBtn);
        ret.add(loadPaperBtn);
        ret.add(quitBtn);
        return ret;
    }
    public MainMenuFrame(){
        setLayout(new BorderLayout());
        add(createBtnPanel(),BorderLayout.SOUTH);
        pack();
        setTitle("Test System");
        setMinimumSize(new Dimension(400,200));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
