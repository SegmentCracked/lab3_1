package answer;

import java.util.LinkedList;
import java.util.List;

public class MapAnswer implements Answer{

	List<String> side1 = new LinkedList<String>();
	List<String> side2 = new LinkedList<String>();
	List<Integer> map = new LinkedList<Integer>();
	
	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		String ret = "";
		for(int i=0; i<map.size(); i++){
			ret += "< "+side1.get(i)+", "+side2.get(map.get(i))+">\n";
		}
		return ret;
	}
	public String[][] getAnswerPairs(){
		String[][] ret = new String[map.size()][2];
		for(int i=0; i<map.size(); i++){
			ret[i][0]=side1.get(i);
			ret[i][1]=side2.get(map.get(i));
		}
		return ret;
	}

	@Override
	public void setAnswer(String answer) {
		// TODO Auto-generated method stub
		String[] answers = answer.split(" ");
		map.clear();
		for(int i=0; i<answers.length; i++){
			map.add(Integer.parseInt(answers[i]));
		}
	}
	
	public void setQuestion(List<String>side1, List<String>side2){
		this.side1 = side1;
		this.side2 = side2;
	}


	@Override
	public String writeAnswer() {
		String ret = "";
		for(int i=0; i<map.size(); i++){
			ret += map.get(i)+" ";
		}
		return ret;
	}


	@Override
	public int getType() {
		return AnswerFactory.AnswerType.MAP;
	}


	@Override
	public boolean match(Answer answer) {
		return answer.writeAnswer().equals(this.writeAnswer());
	}

}
