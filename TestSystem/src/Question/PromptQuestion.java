package question;

import answer.Answer;

public abstract class PromptQuestion extends Question{
	
	public PromptQuestion(int type) {
		super(type);
	}

	@Override
	public abstract Answer getAnswer();
	@Override
	public abstract void setAnswer(String answer);
}
