package Question;

import java.util.LinkedList;
import java.util.List;

import answer.Answer;
import answer.MapAnswer;

public class MapQuestion implements Question {
	
	MapAnswer answer;
	List<String> side1 = new LinkedList<String>();
	List<String> side2 = new LinkedList<String>();
	int side;
	int score;
	String prompt;
	

	public void setItem(String item) {
		// TODO Auto-generated method stub
		if(side == 1){
			side1.add(item);
		}else{
			side2.add(item);
		}
	}

	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		this.answer = new MapAnswer();
		this.answer.setQuestion(side1, side2);
		this.answer.setAnswer(answer);
	}

	@Override
	public Answer getAnswer() {
		// TODO Auto-generated method stub
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.getAnswer().equals(answer);
	}
	
	public void setSide(int side){
		this.side = side;
	}
	
	@Override
	public String getQuestion(){
		String ret = "Map: "+prompt+"\n";
		for(int i=0; i<side1.size(); i++){
			ret += side1.get(i)+"\t"+side2.get(i)+"\n";
		}
		return ret;
	}

	public List<String> getItem() {
		// TODO Auto-generated method stub
		if(side == 1){
			return side1;
		}
		return side2;
	}

	public boolean remove(int index) {
		if(side == 1){
			if(side1.size() > index){
				side1.remove(index);
				return true;
			}
		}else{
			if(side2.size() > index){
				side2.remove(index);
				return true;
			}
		}
		return false;
	}

	public boolean changeItem(int index, String item) {
		if(side == 1){
			if(side1.size() > index){
				side1.remove(index);
				side1.add(index, item);
				return true;
			}
		}else{
			if(side2.size() > index){
				side2.remove(index);
				side2.add(index, item);
				return true;
			}
		}
		return false;
	}

	@Override
	public int getType() {
		return QuestionFactory.QuestionType.MAP;
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

	public boolean changeItemNumber(int num) {
		if(side == 1){
			if(side1.size() > num){
				for(int i = num; i<side1.size(); i++){
					side1.remove(i);
				}
				return true;
			}
		}else{
			if(side2.size() > num){
				for(int i = num; i<side2.size(); i++){
					side2.remove(i);
				}
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
