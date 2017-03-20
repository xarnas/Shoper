package com.arnas.shoper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 01/03/2017.
 */

public class SaveList {

    List<DyGroceriesList3> list = new ArrayList<DyGroceriesList3>();

    public void addList(int id,DyGroceriesList3 dg3) {

      //  list.add(dg3);
       list.add(id,dg3);
    }
    public List returnList()
    {
        return list;
    }

    public void removeItem(int id){

        DyGroceriesList3 dg3 = singleItem(id);

        list.remove(dg3);
    }
    public void updateList(int id){

    }

    public DyGroceriesList3 singleItem(int id){
        return list.get(id);
    }

    boolean contains(int id) {
        for (DyGroceriesList3 item : list) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
