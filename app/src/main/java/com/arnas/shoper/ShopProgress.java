package com.arnas.shoper;

/**
 * Created by arnaspetrauskas on 28/07/2017.
 */

public class ShopProgress {
    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getChildSelected() {
        return childSelected;
    }

    public void setChildSelected(int childSelected) {
        this.childSelected = childSelected;
    }

    public int groupPosition;
    public int childSelected;
}
