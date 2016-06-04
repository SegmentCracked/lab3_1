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
    private ArrayList<Function<String,Void>> itemRemoveListeners, itemAddListeners;
    private JPopupMenu itemListMenu;
    private JMenuItem addItemBtn;
    private JMenuItem removeItemBtn;
    private JMenuItem moveUpBtn;
    private JMenuItem moveDownBtn;
    private JPopupMenu createItemListMenu(){
        itemListMenu=new JPopupMenu();
        addItemBtn = new JMenuItem("Add choice");
        removeItemBtn = new JMenuItem("Remove choice");
        moveUpBtn = new JMenuItem("Move up");
        moveDownBtn = new JMenuItem("Move down");
        itemListMenu.add(addItemBtn);
        itemListMenu.add(removeItemBtn);
        itemListMenu.add(moveUpBtn);
        itemListMenu.add(moveDownBtn);
        itemRemoveListeners =new ArrayList<>();
        itemAddListeners = new ArrayList<>();
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
                    itemAddListeners.stream().forEach(f->{
                        f.apply(s.trim());
                    });
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
        moveUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) return ;
                final int selectedIndex = list.getSelectedIndex();
                if (selectedIndex == 0) return;
                final int targetIndex = selectedIndex - 1;
                final String itemToMove = listModel.get(selectedIndex);
                listModel.remove(selectedIndex);
                listModel.add(targetIndex,itemToMove);
            }
        });
        moveDownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) return ;
                final int selectedIndex = list.getSelectedIndex();
                if (selectedIndex == listModel.size()-1) return ;
                final int targetIndex = selectedIndex + 1;
                final String itemToMove = listModel.get(selectedIndex);
                listModel.remove(selectedIndex);
                listModel.add(targetIndex,itemToMove);
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

    public JPopupMenu getItemListMenu() {
        return itemListMenu;
    }
    public void hideAddMenuItem(){
        itemListMenu.remove(addItemBtn);
    }
    public void hideRemoveMenuItem(){
        itemListMenu.remove(removeItemBtn);
    }
    public DefaultListModel<String> getListModel() {
        return listModel;
    }
    public void addItemRemoveListener(Function<String,Void> listener){
        itemRemoveListeners.add(listener);
    }
    public void addItemAddListener(Function<String,Void> listener){
        itemAddListeners.add(listener);
    }
}
