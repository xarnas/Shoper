package com.arnas.shoper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 23/03/2017.
 */

public class CategoryList implements Parcelable {
    public int id;
    public String name;

    CategoryList(int id,String cat){
        setId(id);
        setName(cat);

    }

    protected CategoryList(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<CategoryList> CREATOR = new Creator<CategoryList>() {
        @Override
        public CategoryList createFromParcel(Parcel in) {
            return new CategoryList(in);
        }

        @Override
        public CategoryList[] newArray(int size) {
            return new CategoryList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
