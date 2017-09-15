
package com.arnas.shoper;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

public class DyGroceriesList3 extends AppCompatActivity implements Parcelable {

    protected String Name;
    protected String Unit;
    protected String ListItem;
    protected int HeadId;
    protected int id;
    protected int activeStatus;

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public static Creator<DyGroceriesList3> getCREATOR() {
        return CREATOR;
    }

    protected DyGroceriesList3(Parcel in) {
        Name = in.readString();
        Unit = in.readString();
        ListItem = in.readString();
        HeadId = in.readInt();
        id = in.readInt();
        activeStatus= in.readInt();
    }

    public static final Creator<DyGroceriesList3> CREATOR = new Creator<DyGroceriesList3>() {
        @Override
        public DyGroceriesList3 createFromParcel(Parcel in) {
            return new DyGroceriesList3(in);
        }

        @Override
        public DyGroceriesList3[] newArray(int size) {
            return new DyGroceriesList3[size];
        }
    };

    public int getHeadId(){
        return HeadId;
    }

    public void setHeadId(int headId){

        this.HeadId=headId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    DyGroceriesList3(int id,String Name, String Unit, String ListItem,int headId){
        setName(Name);
        setUnit(Unit);
        setListItem(ListItem);
        setId(id);
        setHeadId(headId);
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getListItem() {
        return ListItem;
    }

    public void setListItem(String listItem)
    {
        ListItem = listItem;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Unit);
        dest.writeString(ListItem);
        dest.writeInt(HeadId);
        dest.writeInt(id);
        dest.writeInt(activeStatus);
    }
}






