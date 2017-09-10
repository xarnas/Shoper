package com.arnas.shoper;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 04/04/2017.
 */

public class DyMealsList3 extends AppCompatActivity implements Parcelable {

        protected String Name;
        protected int id;


    protected DyMealsList3(Parcel in) {
        Name = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DyMealsList3> CREATOR = new Creator<DyMealsList3>() {
        @Override
        public DyMealsList3 createFromParcel(Parcel in) {
            return new DyMealsList3(in);
        }

        @Override
        public DyMealsList3[] newArray(int size) {
            return new DyMealsList3[size];
        }
    };

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    DyMealsList3(int id,String Name){
            setName(Name);
            setId(id);
        }

        public String getName() {
            return Name;
        }
        public void setName(String name) {
            Name = name;
        }





    }
