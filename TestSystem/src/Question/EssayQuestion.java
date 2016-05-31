package Question;

import answer.Answer;

public class EssayQuestion extends Question {
	public EssayQuestion(){
		super(3);
	}
	
	@Override
	public String getQuestion(){
		return "Essay: "+prompt;
	}

	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		
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
