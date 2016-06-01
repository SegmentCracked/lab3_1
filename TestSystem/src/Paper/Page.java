package Paper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import Question.Question;

public abstract class Page {
	
	String pageName;
	String type;
	
	List<Question> questionList = new LinkedList<Question>();
	
	public void setPageName(String pageName){
		this.pageName = pageName;
	}
	
	public String getPageName(){
		return this.pageName;
	}
	
	public void addQuestion(Question question){
		questionList.add(question);
	}
	
	public Question getQuestion(int index){
		if(index >= questionList.size()){
			return null;
		}else{
			return questionList.get(index);
		}
	}
	
	public int getQuestionSize(){
		return questionList.size();
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public Iterator<Question> iterator(){
		return new IteratorQuestion();
	}
	public void clearPage(){
		questionList.clear();
	}
	class IteratorQuestion implements Iterator<Question> {
		int questionIndex;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if(questionList.size() > questionIndex)
				return true;
			return false;
		}

		@Override
		public Question next() {
			// TODO Auto-generated method stub
			return questionList.get(questionIndex++);
		}
		
		
	}
	
}
