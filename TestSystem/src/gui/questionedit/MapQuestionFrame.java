package gui.questionedit;

import Control.QuestionControl;
import Question.MapQuestion;
import Question.Question;
import Question.QuestionFactory;
import Question.QuestionFactory.QuestionType;
import answer.MapAnswer;
import gui.util.ComboBoxDialog;
import gui.util.MapItemAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Mengxiao Lin on 2016/5/31.
 */
public class MapQuestionFrame extends QuestionFrame{
    private MapQuestion question;
    private JTextField promptTextField;
    private ItemList leftList, rightList;



    private JList<MapItemAdapter> answerList;
    private DefaultListModel<MapItemAdapter> answerListModel;

    private JPanel createChoicePanel(){
        JPanel ret = new JPanel(new GridLayout(1,2));
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        leftList=new ItemList(this);
        rightList= new ItemList(this);
        leftPanel.add(new Label("Left:"),BorderLayout.NORTH);
        leftPanel.add(leftList.getList(), BorderLayout.CENTER);
        rightPanel.add(new Label("Right:"),BorderLayout.NORTH);
        rightPanel.add(rightList.getList(),BorderLayout.CENTER);

        ret.add(leftPanel);
        ret.add(rightPanel);
        return ret;
    }

    private JPanel createAnswerPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("Please set the answer for each item in left side by click the item."),BorderLayout.NORTH);
        answerList = new JList<>();
        answerListModel = new DefaultListModel<>();
        answerList.setModel(answerListModel);
        leftList.addItemAddListener(item->{
            answerListModel.addElement(new MapItemAdapter(item));
            return null;
        });
        leftList.addItemRemoveListener(item->{
            for (int i=0;i<answerListModel.size();++i){
                if (answerListModel.get(i).getLeftItem().equals(item)){
                    answerListModel.remove(i);
                    return null;
                }
            }
            return null;
        });
        answerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (answerList.isSelectionEmpty()) return;
                int selectedIndex = answerList.getSelectedIndex();
                MapItemAdapter item = answerListModel.get(selectedIndex);
                String[] choices = new String[rightList.getListModel().size()];
                for (int i=0;i<rightList.getListModel().size();++i) choices[i]= rightList.getListModel().get(i);
                String answer = ComboBoxDialog.getChoice(MapQuestionFrame.this, "Please the answer for "+item.getLeftItem()+":", choices, item.getRightItem());
                item.setRightItem(answer);
                answerList.setModel(answerListModel);
            }
        });
        ret.add(answerList, BorderLayout.CENTER);
        return ret;
    }
    private JPanel createMainPanel(){
        if (isHasAnswer()){
            JPanel ret = new JPanel(new GridLayout(2,1));
            ret.add(createChoicePanel());
            ret.add(createAnswerPanel());
            return ret;
        }else{
            return createChoicePanel();
        }
    }
    public MapQuestionFrame(boolean hasAnswer) {
        super(hasAnswer);
        setLayout(new BorderLayout());

        JPanel topPanel =new JPanel(new GridLayout(2,1));
        topPanel.add(new JLabel("Prompt:"));
        promptTextField =new JTextField();
        topPanel.add(promptTextField);

        add(topPanel,BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createBottomPanel(),BorderLayout.SOUTH);
        setTitle("Map Question");
        setMinimumSize(new Dimension(350,500));
        pack();
        QuestionFactory factory = new QuestionFactory();
        setQuestion(factory.createQuestion(QuestionFactory.QuestionType.MAP));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question=null;
                MapQuestionFrame.this.setVisible(false);
            }
        });
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionControl questionControl = new QuestionControl();
                String[] side1 = new String[leftList.getListModel().size()];
                for (int i=0;i<leftList.getListModel().size();++i){
                    side1[i]=leftList.getListModel().get(i);
                }
                String[] side2 = new String[rightList.getListModel().size()];
                for (int i=0;i<rightList.getListModel().size();++i){
                    side2[i]=rightList.getListModel().get(i);
                }
                question = (MapQuestion) questionControl.createQuestion(QuestionType.MAP,promptTextField.getText(),side1,side2);
                if (isHasAnswer()){
                    question.setScore(getScoreValue());
                    ArrayList<MapItemAdapter> answerItemList = new ArrayList<MapItemAdapter>();
                    for (int i=0;i<answerListModel.size();++i){
                        answerItemList.add(answerListModel.get(i));
                    }
                    Object[] answerIndices = answerItemList.stream().map(item ->{
                        for (int i=0;i<rightList.getListModel().size();++i){
                            if (rightList.getListModel().get(i).equals(item.getRightItem())){
                                return i;
                            }
                        }
                        return -1;
                    }).toArray();
                    StringBuilder answerStrBuilder = new StringBuilder();
                    for (int i=0;i<answerIndices.length;++i){
                        answerStrBuilder.append(answerIndices[i]);
                        answerStrBuilder.append(" ");
                    }
                    question.setAnswer(answerStrBuilder.toString());
                }
                MapQuestionFrame.this.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                question= null;
            }
        });
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question questionToShow) {
        this.question = (MapQuestion) questionToShow;
        promptTextField.setText(questionToShow.getPrompt());
        question.getSide1().forEach(s->leftList.getListModel().addElement(s));
        question.getSide2().forEach(s->rightList.getListModel().addElement(s));
        if (isHasAnswer()){
            if (questionToShow.getAnswer() ==null){
                question.getSide1().forEach(s-> answerListModel.addElement(new MapItemAdapter(s)));
            }else{
                String[][] answers = ((MapAnswer)question.getAnswer()).getAnswerPairs();
                for (String[] answerPair : answers) {
                    MapItemAdapter item = new MapItemAdapter(answerPair[0]);
                    item.setRightItem(answerPair[1]);
                    answerListModel.addElement(item);
                }
            }
        }
    }
}
