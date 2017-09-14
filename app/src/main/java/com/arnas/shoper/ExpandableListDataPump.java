package com.arnas.shoper;

/**
 * Created by arnaspetrauskas on 21/07/2017.
 */

import android.app.LauncherActivity;

import java.util.ArrayList;
        import java.util.HashMap;
import java.util.Iterator;
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
               boolean Contains=false;
                if (objecti.equals(objectii.getListItem()) && objectii.getActiveStatus() != 1){
                    if (list[iy].size() == 0){
                        list[iy].add(objectii.getName()+" "+objectii.getUnit());
                    }else {
                        for (int x = 0; x < list[iy].size(); x++) {
                            int spaceIndex = list[iy].get(x).toString().toLowerCase().indexOf(" ");
                            if (list[iy].get(x).toString().toLowerCase().substring(0,spaceIndex).equals(objectii.getName().toLowerCase())) {
                                String addUnits = list[iy].get(x).toString().toLowerCase().substring(spaceIndex+1);
                                int endOfobjectString = objectii.getUnit().lastIndexOf(" ");
                                int addUnitIndexSpace = addUnits.indexOf(" ");
                                int addUnitInt=Integer.parseInt(addUnits.substring(0,addUnitIndexSpace));
                                int objectIndexSpace = objectii.getUnit().indexOf(" ");
                                int objectInt = Integer.parseInt(addUnits.substring(0,objectIndexSpace));
                                int totalSum=addUnitInt+objectInt;
                                list[iy].set(x,objectii.getName()+" "+totalSum+" "+objectii.getUnit().substring(endOfobjectString+1));
                                Contains = true;
                            }
                        }
                        if (!Contains){
                            list[iy].add(objectii.getName() + " " + objectii.getUnit());
                        }
                    }
                }
            }
            expandableListDetail.put(objecti.toString(), list[iy]);
            iy=+1;
        }

        return expandableListDetail;
    }
}