package gui.questionedit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by Mengxiao Lin on 2016/5/30.
 */
public class ItemList {
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private Component parentFrame;
    private ArrayList<Function<String,Void>> itemRemoveListeners;
    private JPopupMenu createItemListMenu(){
        JPopupMenu itemListMenu=new JPopupMenu();
        JMenuItem addItemBtn = new JMenuItem("Add choice");
        JMenuItem removeItemBtn = new JMenuItem("Remove choice");
        itemListMenu.add(addItemBtn);
        itemListMenu.add(removeItemBtn);
        itemRemoveListeners =new ArrayList<>();
        list.setBorder(BorderFactory.createLoweredBevelBorder());
        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(parentFrame,"The title of the choice:","Add item", JOptionPane.QUESTION_MESSAGE);
                if (s!=null) {
                    if (listModel.contains(s)){
                        JOptionPane.showConfirmDialog(parentFrame,"The item is added!", "Add item",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                    listModel.addElement(s.trim());
                }
            }
        });
        removeItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) return ;
                int selectedIndex = list.getSelectedIndex();
                final String itemToRemove = listModel.get(selectedIndex);
                listModel.remove(selectedIndex);
                itemRemoveListeners.stream().forEach(f->{
                    f.apply(itemToRemove);
                });
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
    public void addItemRemoveListener(Function<String,Void> listener){
        itemRemoveListeners.add(listener);
    }
}
