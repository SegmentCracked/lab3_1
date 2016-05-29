package answer;

public class DecideAnswer implements Answer{
	
	int anwser;
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = ""+anwser;
		return ret;
	}


	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		this.anwser = Integer.parseInt(answer);
	}


	@Override
	public String writeAnswer() {
		// TODO Auto-generated method stub
		return anwser+"";
	}


	@Override
	public int getType() {
		return AnswerFactory.AnswerType.DECIDE;
	}


	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());
	}

}
