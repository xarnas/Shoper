package com.arnas.shoper;

/**
 * Created by arnaspetrauskas on 30/07/2017.
 */

public class ExpandleListChanger {
    public int groupNumber;
    public int groupSize;

    ExpandleListChanger(int groupNumber, int groupSize){
        setGroupNumber(groupNumber);
        setGroupSize(groupSize);
    }
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
