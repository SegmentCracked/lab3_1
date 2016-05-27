package answer;

public class TextAnswer implements Answer{
	
	String text;
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public void setAnswer(String anwser) {
		// TODO Auto-generated method stub
		text = anwser;
	}

	@Override
	public String writeAnswer() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public int getType() {
		return 2;
	}

	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());
	}

}
