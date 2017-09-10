package com.arnas.shoper;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by arnaspetrauskas on 28/07/2017.
 */

public class ShopProgress implements Parcelable {

    protected ShopProgress(Parcel in) {
        groupPosition = in.readInt();
        childSelected = in.readInt();
    }

    protected ShopProgress() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupPosition);
        dest.writeInt(childSelected);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopProgress> CREATOR = new Creator<ShopProgress>() {
        @Override
        public ShopProgress createFromParcel(Parcel in) {
            return new ShopProgress(in);
        }

        @Override
        public ShopProgress[] newArray(int size) {
            return new ShopProgress[size];
        }
    };

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
