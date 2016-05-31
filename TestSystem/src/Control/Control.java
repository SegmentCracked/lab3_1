package Control;

import java.util.*;

import answer.*;
import Paper.Page;
import Paper.Record;
import Paper.Survey;
import Paper.Test;
import Question.ChoiceQuestion;
import Question.DecideQuestion;
import Question.EssayQuestion;
import Question.ItemQuestion;
import Question.MapQuestion;
import Question.Question;
import Question.RankQuestion;
import Question.ShortEssayQuestion;

public class Control implements Controller {
	
	List<String>[] pageNameList;
	Page page;
	Question question;
	int index;
	Record record;
	IO io = new IO();
	List<String> recordName;
	Iterator<Question> iterator;
	QuestionControl questionControl = new QuestionControl();
	
	public Control(){
		pageNameList = io.readInfo();
	}
	
	public void createPage(int type){
		if(type == 0){
			page = new Survey();
			page.setType("survey");
		}else{
			page = new Test();
			page.setType("test");
		}
	}
		
	public void setPageName(String name){
		page.setPageName(name);
		if(page.getType().equals("test")){
			pageNameList[1].add(name);
		}else{
			pageNameList[0].add(name);
		}
	}
	
	public void saveAnswer(){
		recordName = io.readRecordInfo(page.getPageName());
		recordName.add(page.getPageName()+"-"+record.getPersonName());
		if(page.getType().equals("test")){
			this.grade();
		}
		io.writeReordInfo(page.getPageName(), recordName);
		io.writeRecord(page.getPageName()+"-"+record.getPersonName(), record);
	}
	
	public List<String> getPageName(int type){
		return pageNameList[type];
	}
	
	public void save(){
		if(page.getType().equals("test")){
			Test test = (Test)page;
			test.computeScore();
		}
		io.writeInfo(pageNameList);
		io.writePage(page);
	}
	
	public void loadPage(int index, int type){
		page = io.readPage(pageNameList[type].get(index));
		record = new Record();
	}
	
	public void createQuestion(int type, String prompt, int score, String answer) {
		Question question = questionControl.createQuestion(type, prompt, score, answer);
		page.addQuestion(question);
	}
	
	public void createQuestion(int type, String prompt) {
		Question question = questionControl.createQuestion(type, prompt);
		page.addQuestion(question);
	}
	
	public void createQuestion(int type, String prompt, String[] items, int score, String answer) {
		Question question = questionControl.createQuestion(type, prompt, items, score, answer);
		page.addQuestion(question);
	}
	
	public void createQuestion(int type, String prompt, String[] items) {
		Question question = questionControl.createQuestion(type, prompt, items);
		page.addQuestion(question);
	}
	
	public void createQuestion(int type, String prompt, String[] side1, String[] side2) {
		Question question = questionControl.createQuestion(type, prompt, side1, side2);
		page.addQuestion(question);
	}
	
	public void createQuestion(int type, String prompt, String[] side1, String[] side2, int score, String answer) {
		Question question = questionControl.createQuestion(type, prompt, side1, side2, score, answer);
		page.addQuestion(question);
	}
	
	public List<String> displayPage(int index, int type){
		List<String> ret = new LinkedList<String>();
		if(pageNameList[type].size() == index){
			return ret;
		}else{
			page = io.readPage(pageNameList[type].get(index));
			ret = questionControl.getQuestions(page);
			return ret;
		}
	}
	
	public int modify(int index){
		if(index == page.getQuestionSize()){
			return -1;
		}else{
			question = page.getQuestion(index);
			return questionControl.getType(question);
		}
	}
	
	public void setPrompt(String prompt) {
		questionControl.setPrompt(question, prompt);
	}
	
	public void setAnswer(String answer) {
		questionControl.setAnswer(question, answer);
	}

	public String changeItem(int index, String item){
		if(questionControl.changeItem(question, index, item))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	

	public String changeItemNumber(int num){
		if(questionControl.changeItemNumber(question, num))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	
	public String remove(int index){
		if(questionControl.remove(question, index))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	
	public void setSide(int side) {
		questionControl.setSide(question, side);
	}
	
	public void setRecordName(String name){
		record.setPersonName(name);
		questionControl.initIterator(page);
	}
	
	public String nextQuestion(){
		question = questionControl.nextQuestion(page);
		return questionControl.getQuestion(question);
	}
	
	public boolean hasNextQuestion(){
		return questionControl.hasNextQuestion(page);
	}
	
	public void answerQuestion(String answer){
		Answer answerObject = questionControl.answerQuestion(question,answer);
		record.addAnswer(answerObject);
	}
	
	public void grade(){
		int score = questionControl.grade(page, record);
		record.addScore(score);
	}
	
	public void setScore(int score){
		questionControl.setScore(question, score);
	}
	
	public String getOutcome(int index, int type){
		this.loadPage(index, type);
		recordName = io.readRecordInfo(page.getPageName());
		List<Iterator<Answer>> recordList = new LinkedList<Iterator<Answer>>();
		for(int i=0; i<recordName.size(); i++){
			recordList.add(io.readRecord(recordName.get(i)).iterator());
		}
		Iterator<Question> questionIterator = page.iterator();
		List<String> outcome = new LinkedList<String>();
		outcome = questionControl.getOutcome(page, recordList);
		String ret="" ;
		for(int i=0; i<outcome.size(); i++){
			ret += outcome.get(i);
		}
		return ret;
	}
}
