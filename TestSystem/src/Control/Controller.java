package Control;
import java.util.List;

import Question.Question;

public interface Controller {
	
	public void createPage(int type);
	
	public void setPageName(String name);
	
	public List<String> getPageName(int type);
	
	public List<String> displayPage(int index, int type);
	
	public void save();
	
	public int modify(int index);
	
	public void setPrompt(String prompt);
	
	public void setAnswer(String answer);
	
	public String changeItem(int index, String item);
	
	public String changeItemNumber(int num);
	
	public String remove(int index);
	
	public void setSide(int side);
	
	public void loadPage(int index, int type);
	
	public void setRecordName(String name);
	
	public String nextQuestion();
	
	public boolean hasNextQuestion();
	
	public void answerQuestion(String answer);
	
	public void saveAnswer();
	
	public void grade();
	
	public void setScore(int score);
	
	public String getOutcome(int index, int type);
	
	public void createQuestion(int type, String prompt, int score, String answer);
	
	public void createQuestion(int type, String prompt);
	
	public void createQuestion(int type, String prompt, String[] items, int score, String answer);
	
	public void createQuestion(int type, String prompt, String[] items);
	
	public void createQuestion(int type, String prompt, String[] side1, String[] side2);
	
	public void createQuestion(int type, String prompt, String[] side1, String[] side2, int score, String answer);
}

