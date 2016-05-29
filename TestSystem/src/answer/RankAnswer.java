package answer;

import java.util.LinkedList;
import java.util.List;

public class RankAnswer implements Answer{
	
	List<Integer> rank = new LinkedList<Integer>();
	List<String> question = new LinkedList<String>();
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = "";
		for(int i=0; i<question.size(); i++){
			ret+=" " + question.get(rank.get(i));
		}
		return ret;
	}

	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		String[] answers = answer.split(" ");
		rank.clear();
		for(int i=0; i<answers.length; i++){
			rank.add(Integer.parseInt(answers[i]));
		}
	}
	
	public void setQuestion(List<String> question){
		this.question = question;
	}

	@Override
	public String writeAnswer() {
		String ret = "";
		for(int i=0; i<rank.size(); i++){
			ret += rank.get(i) + " ";
		}
		return ret;
	}

	@Override
	public int getType() {
		return AnswerFactory.AnswerType.RANK;
	}

	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());

	}

}
