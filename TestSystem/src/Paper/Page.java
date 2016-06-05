package Paper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import Question.Question;

public abstract class Page {
	
	String pageName;
	int type;
	int timeLimit;
	String author;
	public static final String TYPE_LIST[]=new String[]{"survey","test"};
	
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
		return TYPE_LIST[type];
	}
	public int getTypeId(){
		return type;
	}
	
	public void setType(String type){
		for (int i=0;i<TYPE_LIST.length;++i){
			if (type.equals(TYPE_LIST[i])) {
				this.type = i;
				return;
			}
		}
		throw new RuntimeException("Unknown type");
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

	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}

