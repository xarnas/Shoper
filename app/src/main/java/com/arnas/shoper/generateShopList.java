package com.arnas.shoper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arnaspetrauskas on 07/09/2017.
 */

public class generateShopList extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    SaveList save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        save = (SaveList) getIntent().getParcelableExtra("SAVE_OBJECT");
        shooperList(getIntent().getStringExtra("GENERATED_SHOP_LIST").toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gener, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.backToMainMenu) {
            Intent intent = new Intent(generateShopList.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public int getColor(View v){
        int color =0;
        if (v.getBackground() != null) {
            color = ((ColorDrawable) v.getBackground()).getColor();
        }
        return color;
    }

    public void colorChanger(ExpandableListView parent, View v,
                             int groupPosition, int childPosition, long id){
        if (getColor(v) == Color.GREEN){
            save.removeShopProgress(groupPosition,childPosition,v,parent);
            int spInt = save.changeShopProgress(groupPosition,childPosition,false);
            v.setBackgroundColor (Color.TRANSPARENT);
            parent.getChildAt(groupPosition+save.getExpandleListChanger(groupPosition)).setBackgroundColor(Color.TRANSPARENT);
       /* Toast.makeText(getApplicationContext(),
                spInt+" of "+parent.getExpandableListAdapter().getChildrenCount(groupPosition), Toast.LENGTH_LONG)
                .show();*/
        }else {
            if(save.addShopProgress(groupPosition,childPosition,v,parent)){
                int spInt = save.changeShopProgress(groupPosition,childPosition,true);
           /* Toast.makeText(getApplicationContext(),
                    "ShopProgresses "+spInt+" of "+parent.getExpandableListAdapter().getChildrenCount(groupPosition), Toast.LENGTH_LONG)
                    .show();
            Toast.makeText(getApplicationContext(),
                    spInt+" of "+parent.getExpandableListAdapter().getChildrenCount(groupPosition), Toast.LENGTH_LONG)
                    .show();*/

                if (spInt == parent.getExpandableListAdapter().getChildrenCount(groupPosition)){
                    parent.getChildAt(groupPosition+save.getExpandleListChanger(groupPosition)).setBackgroundColor(Color.YELLOW);

                }
            }else{
                int spInt  = save.changeShopProgress(groupPosition,childPosition,true);
                /*Toast.makeText(getApplicationContext(),
                        "ShopProgresses "+spInt+" of "+parent.getExpandableListAdapter().getChildrenCount(groupPosition), Toast.LENGTH_LONG)
                        .show();*/
                if (spInt == parent.getExpandableListAdapter().getChildrenCount(groupPosition)){
                    parent.getChildAt(groupPosition+save.getExpandleListChanger(groupPosition)).setBackgroundColor(Color.YELLOW);

                }
            /*Toast.makeText(getApplicationContext(),
                    spInt+" of "+parent.getExpandableListAdapter().getChildrenCount(groupPosition), Toast.LENGTH_LONG)
                    .show();*/
            }
            v.setBackgroundColor(Color.GREEN);
        }

    }


    protected void shooperList(String shoperMeals) {

        setContentView(R.layout.shoperlist);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(shoperMeals,save);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail,save);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                /*Toast.makeText(getApplicationContext(),
                       expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition) + " List extented.",
                        Toast.LENGTH_SHORT).show();*/
                save.addExpandleListChanger(groupPosition,expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition));

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

                /*Toast.makeText(getApplicationContext(),
                        expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition) + " List collpsed.",
                        Toast.LENGTH_SHORT).show();*/

                save.removeExpandleListChanger(groupPosition, expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition));

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                colorChanger(parent,v,groupPosition,childPosition,id);

                return false;
            }
        });
    }

    }
