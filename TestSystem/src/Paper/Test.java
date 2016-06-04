package Paper;

import Question.Question;
import answer.TextAnswer;

public class Test extends Page {
	int totalScore;
	
	public void setTotalScore(int score){
		totalScore = score;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	public int getScoreWithoutTextAnswer(){
		return questionList.stream().map( q->q.getAnswer() instanceof TextAnswer? 0 : q.getScore()).reduce(0, (a,b)->a+b).intValue();
	}
	public void computeScore(){
		totalScore = 0;
		for(int i=0; i<questionList.size(); i++){
			Question question = questionList.get(i);
			totalScore += question.getScore();
		}
	}
}
