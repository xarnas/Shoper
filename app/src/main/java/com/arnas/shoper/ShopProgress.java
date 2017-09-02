package com.arnas.shoper;

import android.view.View;
import android.widget.ExpandableListView;

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

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        this.currentView = currentView;
    }

    public ExpandableListView getParent() {
        return parent;
    }

    public void setParent(ExpandableListView parent) {
        this.parent = parent;
    }

    public View currentView;
    public ExpandableListView parent;
}
