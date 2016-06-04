package gui.questionshower;

import Question.Question;
import Question.MapQuestion;
import gui.questionedit.MapQuestionFrame;
import gui.util.ComboBoxDialog;
import gui.util.MapItemAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class MapQuestionShower extends QuestionShower {
    private MapQuestion question;
    private JLabel promptLabel;
    private JList<MapItemAdapter> itemList;
    private DefaultListModel<MapItemAdapter> itemListModel;
    public MapQuestionShower(Question questionToShow) {
        super(questionToShow);
        setLayout(new BorderLayout());
        this.question = (MapQuestion)questionToShow;
        promptLabel = new JLabel(questionToShow.getPrompt());
        itemList = new JList<MapItemAdapter>();
        itemListModel = new DefaultListModel<>();
        itemList.setModel(itemListModel);
        question.getSide1().forEach(s -> itemListModel.addElement(new MapItemAdapter(s)));
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (itemList.isSelectionEmpty()) return ;
                int selectedIndex = itemList.getSelectedIndex();
                MapItemAdapter item = itemListModel.get(selectedIndex);
                String[] choices = new String[question.getSide2().size()];
                String answer = ComboBoxDialog.getChoice(MapQuestionShower.this, "Please choose the answer of "+item.getLeftItem()+".", choices, item.getRightItem());
                item.setRightItem(answer);
                itemList.setModel(itemListModel);
            }
        });
        add(promptLabel, BorderLayout.NORTH);
        add(itemList, BorderLayout.CENTER);
    }

    @Override
    public void clearInput() {
        for (int i=0;i<itemListModel.size();++i){
            itemListModel.get(i).setRightItem(null);
        }
        itemList.setModel(itemListModel);
    }

    @Override
    public boolean isFilled() {
        for (int i=0;i<itemListModel.size();++i){
            if (itemListModel.get(i).getRightItem() == null) return false;
        }
        return true;
    }

    @Override
    public String getAnswer() {
        return null;
    }
}
