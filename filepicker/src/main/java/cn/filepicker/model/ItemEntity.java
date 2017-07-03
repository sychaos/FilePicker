package cn.filepicker.model;

/**
 * Created by cloudist on 2017/7/2.
 */

public abstract class ItemEntity {

    int type;
    boolean isChecked;

    public ItemEntity(int type) {
        this.type = type;
    }

    abstract int getItemType();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
