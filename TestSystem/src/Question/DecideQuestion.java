package Question;

import answer.Answer;
import answer.DecideAnswer;

public class DecideQuestion extends PromptQuestion {
	DecideAnswer answer;
	String prompt;
	int score;

	@Override
	public String getQuestion(){
		String ret = "T/F: "+prompt+"\n"+"1. right\n2. false";
		return ret;
	}
	
	@Override
	public void setAnswer(String anwser){
		answer = new DecideAnswer();
		answer.setAnswer(anwser);
	}
	
	@Override
	public Answer getAnswer(){
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.getAnswer().equals(answer);
	}

	@Override
	public int getType() {
		return QuestionFactory.QuestionType.DECIDE;
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
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}
}
