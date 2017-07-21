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
                   // final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                  //  checkBoxNew.setId(object.getId());
                   // checkBoxNew.setText(object.getName() + " " + object.getUnit() + " " + object.getListItem());
                    //checcBoxFuncionality(shoperCart, checkBoxNew, 0);

                }

            }
        }



       // list[iy] = new ArrayList<String>();

        for (String objecti : MainList) {
            list[iy] = new ArrayList<String>();
            for (DyGroceriesList3 objectii : FullItemList) {
                if (objecti.equals(objectii.getListItem())){
                    list[iy].add(objectii.getName()+" "+objectii.getUnit());
                }
            }
            expandableListDetail.put(objecti.toString(), list[iy]);
            iy=+1;
           //ItemList.clear();
        }


        /*List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("CRICKET TEAMS", cricket);
        expandableListDetail.put("FOOTBALL TEAMS", football);
        expandableListDetail.put("BASKETBALL TEAMS", basketball);*/
        return expandableListDetail;
    }
}