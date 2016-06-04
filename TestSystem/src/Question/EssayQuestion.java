package Question;

import answer.Answer;
import answer.TextAnswer;

public class EssayQuestion implements Question {

	int score;
	String prompt;
	TextAnswer textAnswer;

	@Override
	public String getQuestion(){
		return "Essay: "+prompt;
	}

	@Override
	public void setAnswer(String answer) {
		textAnswer = new TextAnswer();
		textAnswer.setAnswer(answer);
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
		return textAnswer;
	}
	@Override
	public boolean match(Answer answer) {
		return false;
	}
}
