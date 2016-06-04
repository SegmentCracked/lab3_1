package gui.util;

import gui.EditPaperFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class ComboBoxDialog extends JDialog {
    private JLabel promptLabel;
    private JComboBox<String> comboBox;
    private JButton okButton;
    public ComboBoxDialog(String prompt, String[] choices) {
        this.promptLabel = new JLabel(prompt);
        comboBox =new JComboBox<>();
        okButton = new JButton("OK");
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.add(okButton,BorderLayout.EAST);
        setLayout(new BorderLayout());
        for (String choice : choices) {
            comboBox.addItem(choice);
        }
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(ComboBoxDialog.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        setTitle("Choose answer");
        add(promptLabel,BorderLayout.NORTH);
        add(comboBox,BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        pack();
    }
    public static String getChoice(Component parent, String prompt, String[] choices, String selectedItem){
        ComboBoxDialog comboBoxDialog = new ComboBoxDialog(prompt, choices);
        comboBoxDialog.setLocationRelativeTo(parent);
        comboBoxDialog.setModal(true);
        for (int i=0;i<choices.length;++i){
            if (choices[i].equals(selectedItem)){
                comboBoxDialog.comboBox.setSelectedIndex(i);
                break;
            }
        }
        comboBoxDialog.setVisible(true);
        return (String)comboBoxDialog.comboBox.getSelectedItem();
    }
}
