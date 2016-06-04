package gui.util;

/**
 * Created by Mengxiao Lin on 2016/6/4.
 */
public class MapItemAdapter {
    private String leftItem, rightItem;

    public MapItemAdapter(String leftItem) {
        this.leftItem = leftItem;
        this.rightItem = null;
    }

    public void setLeftItem(String leftItem) {
        this.leftItem = leftItem;
    }

    public void setRightItem(String rightItem) {
        this.rightItem = rightItem;
    }

    public String getRightItem() {
        return rightItem;
    }

    public String getLeftItem() {
        return leftItem;
    }

    @Override
    public String toString() {
        return leftItem + " <=> " + rightItem;
    }
}
