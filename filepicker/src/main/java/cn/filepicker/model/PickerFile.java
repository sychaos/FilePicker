package cn.filepicker.model;

/**
 * Created by cloudist on 2017/6/30.
 */

public class PickerFile extends ItemEntity {

    String name;
    String location;
    long size;


    public PickerFile(int type) {
        super(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PickerFile) {
            PickerFile file = (PickerFile) obj;
            if (this.location.equals(file.getLocation())) {
                return true;
            }
        }
        return false;
    }
}
