package Question;

import java.util.LinkedList;
import java.util.List;

import answer.Answer;
import answer.ChoiceAnswer;

public class ChoiceQuestion extends ItemQuestion {
	List<String> items = new LinkedList<String>();
	ChoiceAnswer answer;
	int score;
	String prompt;

	@Override
	public void clearItem() {
		items.clear();
	}


	@Override
	public void addItem(String item) {
		this.items.add(item);
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = new ChoiceAnswer();
		this.answer.setItem(items);
		this.answer.setAnswer(answer);
	}

	@Override
	public Answer getAnswer() {
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.equals(answer);
	}
	
	@Override
	public String getQuestion(){
		String ret = "question.ChoiceQuestion: "+prompt+"\n";
		for(int i=0; i<items.size(); i++){
			ret += items.get(i)+"\n";
		}
		return ret;
	}

	@Override
	public int getType() {
		return QuestionFactory.QuestionType.CHOICE;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public String getPrompt() {
		return prompt;
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	@Override
	public List<String> getItem() {
		return items;
	}

	@Override
	public boolean remove(int index) {
		if(items.size() > index){
			items.remove(index);
			return true;
		}
		return false;
	}

	@Override
	public boolean changeItem(int index, String item) {
		if(items.size() > index){
			items.remove(index);
			items.add(index, item);
			return true;
		}
		return false;
	}

	@Override
	public boolean changeItemNumber(int num) {
		if(items.size() > num){
			for(int i=num; i<items.size(); i++){
				items.remove(i);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}

}
