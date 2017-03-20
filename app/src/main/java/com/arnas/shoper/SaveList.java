package com.arnas.shoper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 01/03/2017.
 */

public class SaveList {

    List<DyGroceriesList3> list = new ArrayList<DyGroceriesList3>();

    private int getAdapterItemPosition(int id)
    {
        for (int position=0; position < list.size(); position++)
            if (list.get(position).getId() == id)
                return position;
        return 0;
    }

    public void addList(DyGroceriesList3 dg3) {

      //  list.add(dg3);
       list.add(dg3);
    }
    public List returnList()
    {
        return list;
    }

    public void removeItem(int id){

        int possition = getAdapterItemPosition(id);
        DyGroceriesList3 dg3 = singleItem(possition);

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
