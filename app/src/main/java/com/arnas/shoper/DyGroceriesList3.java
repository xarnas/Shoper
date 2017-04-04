
package com.arnas.shoper;


import android.support.v7.app.AppCompatActivity;

public class DyGroceriesList3 extends AppCompatActivity {

    protected String Name;
    protected String Unit;
    protected String ListItem;
    protected int HeadId;
    protected int id;


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

    public void setListItem(String listItem) {
        ListItem = listItem;
    }



}






