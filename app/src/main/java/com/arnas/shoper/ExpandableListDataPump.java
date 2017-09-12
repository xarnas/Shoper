package com.arnas.shoper;

/**
 * Created by arnaspetrauskas on 21/07/2017.
 */

import android.app.LauncherActivity;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class ExpandableListDataPump {
     private static String listItem;
    public static HashMap<String, List<String>> getData(String shoperMeals, SaveList save) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        int iy =0;
        listItem="";
        List<String> MainList = new ArrayList<String>();
        ArrayList[] list = new ArrayList[999];
        //List<String> ItemList = new ArrayList<String>();
        List<DyGroceriesList3> FullItemList = new ArrayList<DyGroceriesList3>();
        String[] meals = shoperMeals.split(";");
        for (int i = 0; i < meals.length; i++) {
            ArrayList<DyGroceriesList3> tmplist = save.fullListHead(Integer.parseInt(meals[i]));
            if (tmplist != null) {
                for (DyGroceriesList3 object : tmplist) {
                     if (!listItem.equals(object.getListItem())){
                         MainList.add(object.getListItem());
                         listItem=object.getListItem();
                     }
                     FullItemList.add(object);

                }

            }
        }

        for (String objecti : MainList) {
            list[iy] = new ArrayList<String>();
            for (DyGroceriesList3 objectii : FullItemList) {
                if (objecti.equals(objectii.getListItem()) && objectii.getActiveStatus() != 1){
                    list[iy].add(objectii.getName()+" "+objectii.getUnit());
                }
            }
            expandableListDetail.put(objecti.toString(), list[iy]);
            iy=+1;
        }

        return expandableListDetail;
    }
}