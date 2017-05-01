package com.arnas.shoper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 01/03/2017.
 */

public class SaveList {

    List<DyGroceriesList3> list = new ArrayList<DyGroceriesList3>();
    List<DyMealsList3> list2 = new ArrayList<DyMealsList3>();
    List<CategoryList> cList = new ArrayList<CategoryList>();
    int catid=0;

SaveList(){

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
    for(int i=0; i <myCat.length-1 ;i++){
        this.catid+=1;
        CategoryList ct1 = new CategoryList(catid,myCat[i]);
        addCategory(ct1);

    }



}


    private int getAdapterItemPosition(int id)
    {
        for (int position=0; position < list.size(); position++)
            if (list.get(position).getId() == id)
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

}
