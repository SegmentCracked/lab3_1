package answer;

import java.util.List;

public class ChoiceAnswer implements Answer{
	
	int[] answer;
	List<String> item;
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = "";
		for(int i = 0; i< answer.length; i++){
			ret += item.get(answer[i]);
		}
		return ret;
	}
	
	@Override
	public String writeAnswer(){
		String ret = "";
		for(int i = 0; i< answer.length; i++){
			ret += answer[i] + " ";
		}
		return ret;
	}
	
	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		String[] answers = answer.split(" ");
		this.answer = new int[answers.length];
		for(int i=0; i<answer.length(); i++){
			this.answer[i] = Integer.parseInt(answers[i]);
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

	public int[] getAnswers(){
		return answer.clone();
	}
	
}
