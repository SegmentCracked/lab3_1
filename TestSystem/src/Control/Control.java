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
	
	public void createQuestion(Question question) {
		QuestionController.createQuestion(page, question);
	}
	
	public List<String> getPageName(int type){
		return pageNameList[type];
	}
	
	public List<String> displayPage(int index, int type){
		List<String> ret = new LinkedList<String>();
		if(pageNameList[type].size() <= index){
			return ret;
		}else{
			page = io.readPage(pageNameList[type].get(index));
			ret = QuestionController.getQuestions(page);
			return ret;
		}
	}
	
	public void save(){
		if(page.getType().equals("test")){
			Test test = (Test)page;
			test.computeScore();
		}
		io.writeInfo(pageNameList);
		io.writePage(page);
	}
	
	public int modify(int index){
		if(index >= page.getQuestionSize()){
			return -1;
		}else{
			question = page.getQuestion(index);
			return QuestionController.getType(question);
		}
	}
	
	public void setPrompt(String prompt) {
		QuestionControl.setPrompt(question, prompt);
	}
	
	public void setAnswer(String answer) {
		QuestionControl.setAnswer(question, answer);
	}

	public String changeItem(int index, String item){
		if(QuestionController.changeItem(question, index, item))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	

	public String changeItemNumber(int num){
		if(QuestionController.changeItemNumber(question, num))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	
	public String remove(int index){
		if(QuestionController.remove(question, index))
			return "Ok, it has changed";
		else
			return "We don't have this item";
	}
	
	public void setSide(int side) {
		QuestionController.setSide(question, side);
	}
	
	public void loadPage(int index, int type){
		page = io.readPage(pageNameList[type].get(index));
		record = new Record();
	}
	
	public void setRecordName(String name){
		record.setPersonName(name);
		QuestionController.initIterator(page);
	}
	
	public String nextQuestion(){
		question = QuestionController.nextQuestion(page);
		return QuestionController.getQuestion(question);
	}
	
	public boolean hasNextQuestion(){
		return QuestionController.hasNextQuestion(page);
	}
	
	public void answerQuestion(String answer){
		Answer answerObject = QuestionController.answerQuestion(question,answer);
		record.addAnswer(answerObject);
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
	
	public void grade(){
		int score = QuestionController.grade(page, record);
		record.addScore(score);
	}
	
	public void setScore(int score){
		question.setScore(score);
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
		outcome = QuestionController.getOutcome(page, recordList);
		String ret = "";
		for(int i=0; i<outcome.size(); i++){
			ret += outcome.get(i)+"\n";
		}
		return ret;
	}
}
