package Question;

import answer.Answer;


public interface Question {
/*
	String prompt;
	int score;
	int type;
*/

	/*
		public Question(int type){
            this.type = type;
        }
    */
	public int getType();

	public String getQuestion();

	public String getPrompt();

	public void setPrompt(String prompt);

	public void setScore(int score);

	public int getScore();

	public void setAnswer(String answer);

	public Answer getAnswer();

	public boolean match(Answer answer);


/*
	public int getType(){
		return type;
	}
	
	public String getQuestion(){
		return null;
	}
	
	public String getPrompt(){
		return prompt;
	}
	
	public void setPrompt(String prompt){
		this.prompt = prompt;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public abstract void setAnswer(String answer);
	
	public abstract Answer getAnswer();
	public abstract boolean match(Answer answer);
*/
}
