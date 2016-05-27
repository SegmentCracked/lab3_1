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
	public void setAnswer(String anwser) {
		// TODO Auto-generated method stub
		this.anwser = Integer.parseInt(anwser);
	}


	@Override
	public String writeAnswer() {
		// TODO Auto-generated method stub
		return anwser+"";
	}


	@Override
	public int getType() {
		return 0;
	}


	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());
	}

}
