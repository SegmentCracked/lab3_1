package Question;

import answer.Answer;
import answer.TextAnswer;

public class ShortEssayQuestion extends PromptQuestion {
	
	TextAnswer answer;
	
	public ShortEssayQuestion() {
		super(2);
		// TODO Auto-generated constructor stub
	}	
	
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
	public Answer getAnswer() {
		// TODO Auto-generated method stub
		return answer;
	}


	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}
}
