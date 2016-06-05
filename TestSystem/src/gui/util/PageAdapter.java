package gui.util;

import Paper.Page;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class PageAdapter {
    private String name;
    private int index;
    private int type;
    private int timeLimit;
    public final static int SURVEY = 0;
    public final static int TEST = 1;

    public PageAdapter(int index, Page page) {
        this.name = page.getPageName();
        this.index = index;
        this.type = page.getTypeId();
        this.timeLimit = page.getTimeLimit();
    }

    @Override
    public String toString() {
        String ret = "Page Name: "+name +"\nType: ";
        if (type == SURVEY) ret+="Survey";
        if (type == TEST) ret+="Test";
        if (timeLimit!=0) {
            ret += "\nTime Limit: ";
            ret += timeLimit;
            ret += " min";
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getType() {
        return type;
    }
}
