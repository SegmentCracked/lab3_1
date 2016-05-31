package gui.questionedit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class ItemList {
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private Component parentFrame;
    private JPopupMenu createItemListMenu(){
        JPopupMenu itemListMenu=new JPopupMenu();
        JMenuItem addItemBtn = new JMenuItem("Add choice");
        JMenuItem removeItemBtn = new JMenuItem("Remove choice");
        itemListMenu.add(addItemBtn);
        itemListMenu.add(removeItemBtn);
        list.setBorder(BorderFactory.createLoweredBevelBorder());
        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(parentFrame,"The title of the choice:","Add choice", JOptionPane.QUESTION_MESSAGE);
                if (s!=null) listModel.addElement(s.trim());
            }
        });
        removeItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) return ;
                int selectedIndex = list.getSelectedIndex();
                listModel.remove(selectedIndex);
            }
        });
        return itemListMenu;
    }

    public ItemList(Component parentFrame){
        this.parentFrame = parentFrame;
        list = new JList<>();
        listModel = new DefaultListModel<>();
        list.setModel(listModel);
        list.setComponentPopupMenu(createItemListMenu());
    }

    public JList<String> getList() {
        return list;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }
}
