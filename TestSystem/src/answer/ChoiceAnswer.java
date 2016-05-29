package answer;

import java.util.List;

public class ChoiceAnswer implements Answer{
	
	int[] anwser;
	List<String> item;
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = "";
		for(int i=0; i<anwser.length; i++){
			ret += item.get(anwser[i]);
		}
		return ret;
	}
	
	@Override
	public String writeAnswer(){
		String ret = "";
		for(int i=0; i<anwser.length; i++){
			ret += anwser[i] + " ";
		}
		return ret;
	}
	
	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		String[] anwsers = answer.split(" ");
		this.anwser = new int[anwsers.length];
		for(int i=0; i<answer.length(); i++){
			this.anwser[i] = Integer.parseInt(anwsers[i]);
		}
	}
	
	public void setItem(List<String> item){
		this.item = item;
	}

	@Override
	public int getType() {
		return AnswerFactory.AnswerType.CHOICE;
	}

	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());
	}
	
	
}
