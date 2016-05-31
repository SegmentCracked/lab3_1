package Question;

import answer.Answer;

public class EssayQuestion implements Question {

	int score;
	String prompt;

	@Override
	public String getQuestion(){
		return "Essay: "+prompt;
	}

	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getType() {
		return QuestionFactory.QuestionType.ESSAY;
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
	public Answer getAnswer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean match(Answer answer) {
		// TODO Auto-generated method stub
		return false;
	}
}
