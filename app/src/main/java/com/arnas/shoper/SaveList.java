package com.arnas.shoper;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 01/03/2017.
 */

public class SaveList {

    List<DyGroceriesList3> list = new ArrayList<DyGroceriesList3>();
    List<DyMealsList3> list2 = new ArrayList<DyMealsList3>();
    List<CategoryList> cList = new ArrayList<CategoryList>();
    List<ShopProgress> shopProgressesList = new ArrayList<ShopProgress>();
    List<ExpandleListChanger> elc = new ArrayList<ExpandleListChanger>();
     public int lastID;

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getCatid() {
        return catid;
    }

    public int catid=0;

SaveList() {
    setLastID(0);
    String[] myCat = {"Pieno gaminiai ir kiaušiniai",
            "Mėsa",
            "Žuvis",
            "Miltiniai gaminiai ir košės",
            "Duonos gaminiai ir konditerija",
            "Daržovės ir vaisiai",
            "Higienos prekės",
            "Konservuoti gaminiai",
            "Kava,Kakava ir Arbata",
            "Saldumynai",
            "Šaldytas maistas",
            "Namų priežiuros prekės",
            "Kūdikiu ir vaikų prekės",
            "Alkoholiniai gėrimai"
    };
    for (int i = 0; i < myCat.length - 1; i++) {
        this.catid += 1;
        CategoryList ct1 = new CategoryList(catid, myCat[i]);
        addCategory(ct1);

    }
}
public void addExpandleListChanger(int groupnumber,int groupsize){
    ExpandleListChanger elcitem = new ExpandleListChanger(groupnumber,groupsize);
    elc.add(elcitem);

}
public void removeExpandleListChanger(int groupnumber,int groupsize){


    for (ExpandleListChanger object: elc) {
        if (object.getGroupNumber() == groupnumber && object.getGroupSize()==groupsize){
            elc.remove(object);
            break;

        }
    }

}
public List<ExpandleListChanger> eclFullList(){
      return elc;
}

public int getExpandleListChanger(int groupnumber){
    int totalSum=0;
    for (ExpandleListChanger object: elc) {
        if (object.getGroupNumber() < groupnumber){
            totalSum+=object.getGroupSize();
        }
    }
    return totalSum;
}
public void clearExpandleListChanger(){
    elc.clear();
}

public boolean addRemoveShopProgress(int groupId, int childid, View currentView, ExpandableListView parent){
    for (ShopProgress object: shopProgressesList) {
        if (object.getGroupPosition()== groupId && object.getChildSelected() == childid){
            return false;
        }
    }
     ShopProgress sp = new ShopProgress();
     sp.setGroupPosition(groupId);
     sp.setChildSelected(childid);
     sp.setCurrentView(currentView);
     sp.setParent(parent);
     shopProgressesList.add(sp);
    return true;
}
public void clearShopProgressList(){
    shopProgressesList.clear();
}

public int changeShopProgress(int groupId,int childid,boolean prog){
    int totalSelected=0;
    for (ShopProgress object: shopProgressesList) {
        if (object.getGroupPosition()==groupId) {
          totalSelected=+1;
        }
    }
    return totalSelected;

}

public List<ShopProgress> fullListShopProgList(){
    return shopProgressesList;
}
public void UpdateCatItem(int id, CategoryList newName ){
         cList.set(id,newName);
}

    private int getAdapterItemPosition(int id)
    {
        for (int position=0; position < list.size(); position++)
            if (list.get(position).getId() == id)
                return position;
        return 0;
    }

    private int getAdapterItemPositionCat(int id)
    {
        for (int position=0; position < cList.size(); position++)
            if (cList.get(position).getId() == id)
                return position;
        return 0;
    }

    public ArrayList<DyGroceriesList3> fullListHead(int headid){
        List<DyGroceriesList3> tmplist = new ArrayList<DyGroceriesList3>();

        for (DyGroceriesList3 object: list) {
          if (object.HeadId==headid){
              tmplist.add(object);
          }
        }
        return (ArrayList<DyGroceriesList3>) tmplist;
    }

    public void addList(DyGroceriesList3 dg3) {

      //  list.add(dg3);
       list.add(dg3);


    }


    public List returnList()
    {
        return list;
    }


    public void removeItem(int id,int openHeadid){

        int possition = getAdapterItemPosition(id);
        DyGroceriesList3 dg3 = singleItem(possition, openHeadid);

        list.remove(dg3);
    }
    public void removeItem(int id){

        int possition = getAdapterItemPosition(id);
        DyMealsList3 dm3 = singleItem(possition);

        list2.remove(dm3);
    }

    public void removeItemCat(int id){

        int possition = getAdapterItemPositionCat(id);
        CategoryList catItem = cList.get(possition);

        cList.remove(catItem);
    }

    public void updateList(int id){

    }

    public DyGroceriesList3 singleItem(int id,int openHeadId){

        for (DyGroceriesList3 item : list) {
            if (item.getId() == id && item.HeadId == openHeadId) {
                return item;
            }
        }
        return null;

    }

    public DyMealsList3 singleItem(int id){

        for (DyMealsList3 item : list2) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;

    }

    boolean contains(int id) {
        for (DyGroceriesList3 item : list) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addListDML3(DyMealsList3 meal){
        list2.add(meal);

    }

    public List<DyMealsList3> fullListDML3(){

        return list2;
    }

    public void addCategory(CategoryList cl){
        cList.add(cl);
    }

    public List<CategoryList> fullListCATG(){

        return cList;
    }

    public int GroceriesLastId(int headId){
        int currentIdValue=0;
        for (DyGroceriesList3 object: list){
             if (object.HeadId == headId){
                 if (object.id > currentIdValue){
                      currentIdValue =object.id;
                 }
             }
        }
        return currentIdValue;
    }
    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public int getLastID() {
        return lastID;
    }


}
