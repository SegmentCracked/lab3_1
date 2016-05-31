package Question;

import answer.Answer;

public abstract class PromptQuestion implements Question {
	@Override
	public abstract Answer getAnswer();
	@Override
	public abstract void setAnswer(String answer);
}
