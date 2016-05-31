package Question;

import java.util.List;

public abstract class ItemQuestion implements Question {
	public abstract void addItem(String item);
	public abstract void clearItem();
	public abstract List<String> getItem();
	public abstract boolean remove(int index);
	public abstract boolean changeItem(int index, String item);
	public abstract boolean changeItemNumber(int num);
}
