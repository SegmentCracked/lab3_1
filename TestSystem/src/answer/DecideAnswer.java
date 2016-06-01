package answer;

public class DecideAnswer implements Answer{
	
	int answer;
	public static final String RIGHT = "1";
	public static final String FALSE = "2";
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = ""+ answer;
		return ret;
	}


	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		this.answer = Integer.parseInt(answer);
	}


	@Override
	public String writeAnswer() {
		// TODO Auto-generated method stub
		return answer +"";
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
