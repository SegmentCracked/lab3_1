package Control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Question.Question;
import Question.ItemQuestion;
import Paper.Page;
import Question.MapQuestion;
import answer.Answer;
import Paper.Record;
import Question.QuestionFactory;
public class QuestionControl {
	Iterator<Question> iterator;
	private Question currentQuestion;
	QuestionFactory x=new QuestionFactory();
	public List<String> getQuestions(Page page){
		List<String> ret = new LinkedList<String>();
        for(int i=0;i<page.getQuestionSize();i++)
        	ret.add(page.getQuestion(i).getQuestion());
		return ret;
	}
	
    public int getType(Question question){
    	return question.getType();
    }
    public void setAnswer(Question question,String answer){
    	question.setAnswer(answer);
    }
    public void setPrompt(Question question,String prompt){
    	question.setPrompt(prompt);
    }
    public Question createQuestion(int type, String prompt){
    	Question question=x.createQuestion(type);
    	question.setPrompt(prompt);
    	return question;
    }
    public Question createQuestion(int type, String prompt, int score, String answer){
    	Question question=x.createQuestion(type);
    	question.setPrompt(prompt);
    	question.setScore(score);
    	question.setAnswer(answer);
    	return question;
    }
    public Question createQuestion(int type, String prompt, String[] items, int score, String answer){
    	Question question=x.createQuestion(type);
    	ItemQuestion itemquestion=(ItemQuestion)question;
    	itemquestion.setPrompt(prompt);
    	itemquestion.setScore(score);
    	itemquestion.setAnswer(answer);
    	for(int i=0;i<items.length;i++)
    		itemquestion.addItem(items[i]);
    	return itemquestion;
    }
    public Question createQuestion(int type, String prompt, String[] items){
    	Question question=x.createQuestion(type);
    	ItemQuestion itemquestion=(ItemQuestion)question;
    	itemquestion.setPrompt(prompt);
    	for(int i=0;i<items.length;i++)
    		itemquestion.addItem(items[i]);
    	return itemquestion;
    }
    public Question createQuestion(int type, String prompt, String[] side1, String[] side2){
    	Question question=x.createQuestion(type);
    	MapQuestion map=(MapQuestion)question;
    	question.setPrompt(prompt);
    	for(int i=0;i<side1.length;i++){
    		map.setSide(1);
    		map.setItem(side1[i]);
    		map.setSide(2);
    		map.setItem(side2[i]);
    	}
    	return map;
    }
    public Question createQuestion(int type, String prompt, String[] side1, String[] side2,int score, String answer){
    	Question question=x.createQuestion(type);
    	MapQuestion map=(MapQuestion)question;
    	map.setPrompt(prompt);
    	for(int i=0;i<side1.length;i++){
    		map.setSide(1);
    		map.setItem(side1[i]);
    		map.setSide(2);
    		map.setItem(side2[i]);
    	}
    	map.setScore(score);
    	map.setAnswer(answer);
    	return map;
    }
    
    public boolean changeItem(Question question,int index,String item){
    	ItemQuestion x=(ItemQuestion) question;
    	boolean t=x.changeItem(index, item);
    	return t;
    }
    public boolean changeItemNumber(Question question,int num){
    	ItemQuestion x=(ItemQuestion) question;
    	boolean t=x.changeItemNumber(num);
    	return t;
    }
    public boolean remove(Question question,int index){
    	ItemQuestion x=(ItemQuestion) question;
    	boolean t=x.remove(index);
    	return t;
    }
    public void setSide(Question question,int side){
    	MapQuestion x=(MapQuestion) question;
    	x.setSide(side);
    }
    public void initIterator(Page page){
    	iterator=page.iterator();
    }
    public Question nextQuestion(Page page){
    	return currentQuestion = iterator.next();
    }
    public String getQuestion(Question question){
    	return question.getQuestion();
    }
    public boolean hasNextQuestion(Page page){
    	return iterator.hasNext();
    }
	public Question getCurrentQuestion(){
		return currentQuestion;
	}
    public Answer answerQuestion(Question question,String answer){
    	question.setAnswer(answer);
    	return question.getAnswer();
    }
    public int grade(Page page,Record record){
    	int total=0;
    	for(int i=0;i<page.getQuestionSize();i++)
    		if(page.getQuestion(i).match(record.getAnswer(i)))
    			total=total+page.getQuestion(i).getScore()+total;
    	return total;
    }
	public void setScore(Question question, int score) {
		// TODO Auto-generated method stub
		question.setScore(score);
	}
	public List<String> getOutcome(Page page,List<Iterator<Answer>> recordList,Iterator<Question> questionIterator){
		List<String> result = new LinkedList<String>();
		while(questionIterator.hasNext()){
			Question question = questionIterator.next();
			Map<String, Integer> map = new HashMap<String, Integer>();
			for(int i=0; i<recordList.size(); i++){
				Answer answer = recordList.get(i).next();
				if(map.containsKey(answer.writeAnswer())){
					int value = map.get(answer.writeAnswer());
					map.put(answer.writeAnswer(), value+1);
				}else{
					map.put(answer.writeAnswer(), 1);
				}
			}
			String oneOutcome = question.getQuestion();
			for(String key: map.keySet()){
				oneOutcome +="Answer: " + key+"\t"+map.get(key)+"\n";
			}
			result.add(oneOutcome);
		}
		return result;
		
	}
}
