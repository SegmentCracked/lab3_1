package answer;

public interface Answer {
	
	
	public String getAnswer();
	public void setAnswer(String aswser);
	public String writeAnswer();
	public int getType();
	public boolean match(Answer answer);
}
