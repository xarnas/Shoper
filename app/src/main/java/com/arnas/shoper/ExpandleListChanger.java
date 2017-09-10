package com.arnas.shoper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arnaspetrauskas on 30/07/2017.
 */

public class ExpandleListChanger implements Parcelable {
    public int groupNumber;
    public int groupSize;

    ExpandleListChanger(int groupNumber, int groupSize){
        setGroupNumber(groupNumber);
        setGroupSize(groupSize);
    }

    protected ExpandleListChanger(Parcel in) {
        groupNumber = in.readInt();
        groupSize = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupNumber);
        dest.writeInt(groupSize);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpandleListChanger> CREATOR = new Creator<ExpandleListChanger>() {
        @Override
        public ExpandleListChanger createFromParcel(Parcel in) {
            return new ExpandleListChanger(in);
        }

        @Override
        public ExpandleListChanger[] newArray(int size) {
            return new ExpandleListChanger[size];
        }
    };

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSzie) {
        this.groupSize = groupSzie;
    }
}
