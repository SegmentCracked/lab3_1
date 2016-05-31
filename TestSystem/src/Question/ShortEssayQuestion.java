package Question;

import answer.Answer;
import answer.TextAnswer;

public class ShortEssayQuestion extends PromptQuestion {
	
	TextAnswer answer;
	int score;
	String prompt;
	
	@Override
	public String getQuestion(){
		return "Text: "+prompt;
	}
	
	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		
		this.answer = new TextAnswer();
		this.answer.setAnswer(answer);
	}

	@Override
	public int getType() {
		return QuestionFactory.QuestionType.SHORTQUESTION;
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
		return answer;
	}


	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}
}
