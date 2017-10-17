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

                            //int spaceIndex = list[iy].get(x).toString().toLowerCase().indexOf(" ");
                            String FullStringObject = list[iy].get(x).toString().toLowerCase();
                            //int spaceIndex = FullItemList.toString().toLowerCase().indexOf(" ");
                            int spaceIndex=0;
                            String tmpvalue = FullStringObject;
                            int spaceIndexY=0;
                            int xspace=0;
                            while (true){
                                try {
                                    spaceIndexY = tmpvalue.toString().toLowerCase().indexOf(" ");
                                    String tmpString = tmpvalue.toString().toLowerCase().substring(spaceIndexY + 1).trim();
                                    String tmpStringF=tmpString.substring(0,1);
                                    spaceIndex=spaceIndex+(spaceIndexY);
                                    Integer.parseInt(tmpStringF);
                                    break;
                                }catch (Exception e){
                                    tmpvalue = tmpvalue.toString().toLowerCase().substring(spaceIndexY+1);
                                    xspace++;
                                }
                            }

                            spaceIndex=spaceIndex+xspace;
                            if (list[iy].get(x).toString().toLowerCase().substring(0,spaceIndex).equals(objectii.getName().toLowerCase())) {
                                String addUnits = list[iy].get(x).toString().toLowerCase().substring(spaceIndex+1).trim();
                                int endOfobjectString = objectii.getUnit().lastIndexOf(" ");
                                int addUnitIndexSpace = addUnits.indexOf(" ");
                                int addUnitInt=0;
                                try {
                                    addUnitInt = Integer.parseInt(addUnits.substring(0, addUnitIndexSpace).trim());
                                }catch (Exception e){

                                }
                                int objectIndexSpace = objectii.getUnit().indexOf(" ");
                                int objectInt=0;
                                try{
                                    objectInt = Integer.parseInt(objectii.getUnit().substring(0,objectIndexSpace).trim());
                                }catch (Exception e){

                                }

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