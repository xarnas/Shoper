
package com.arnas.shoper;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;


public class MainActivity extends AppCompatActivity {

    ListView listView ;
    String[] values= new String[1];
    SaveList save = new SaveList();
    String checkBoxValue;
    int xcount=0;
    int headid =0;

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    public void mainListItem(final CheckBox checkBox, final TableLayout Table, String Course,int headid) {


        if (checkBox != null){


        }
                checkBoxValue=Course;

                if (checkBox == null) {
                    //final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                    //checkBoxNew.setId(headid);
                    //DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(),inputName.getText().toString(),inputUnit.getText().toString(),spinner1.getSelectedItem().toString());
                    //save.addList(dg3);
                    //xcount+=1;
                    //checkBoxNew.setText(checkBoxValue);
                    DyMealsList3 DML3 = new DyMealsList3(headid,Course);
                    save.addListDML3(DML3);
                    ///checcBoxFuncionality(Table,checkBoxNew,1);


                } else {
                   /* DyGroceriesList3 dg3 =  save.singleItem(checkBox.getId());
                    dg3.setName(inputName.getText().toString());
                    dg3.setUnit(inputUnit.getText().toString());
                    dg3.setListItem(spinner1.getSelectedItem().toString());
                    checkBox.setText(checkBoxValue);*/

                }


            }


    public void addItemsOnSpinner(final CheckBox checkBox, final TableLayout Table) {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = layoutInflater.inflate(R.layout.registry, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);




        final Spinner spinner1 = (Spinner) popupWindow.getContentView().findViewById(R.id.spinner1);

        List<String> list = new ArrayList<String>();
        final List<CategoryList> tmpCatlist = save.fullListCATG();

        for (CategoryList object:tmpCatlist){
           list.add(object.getName().toString());
        }





        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getBaseContext(), "Listiner test "+spinner1.getId(), Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        spinner1.setAdapter(dataAdapter);

        Button btnAcppect = (Button) popupView.findViewById(R.id.saveitem);
        Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
        final EditText inputName = (EditText)popupView.findViewById(R.id.itemName);
        final EditText inputUnit = (EditText)popupView.findViewById(R.id.itemUnit);

        spinner1.setSelection(2);

       // if (!checkBox.getText().toString().isEmpty()) {
        if (checkBox != null){
            DyGroceriesList3 dgl3=save.singleItem(checkBox.getId());
            inputName.setText(dgl3.getName().toString());
            inputUnit.setText(dgl3.getUnit().toString());

            spinner1.setSelection(getIndex(spinner1, dgl3.getListItem().toString()));

        }

        btnAcppect.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                if (inputName.getText().toString().isEmpty() || inputUnit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Užpildykite visus laukus", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                checkBoxValue=inputName.getText().toString()+" "+inputUnit.getText().toString()+" "+spinner1.getSelectedItem().toString();

                if (checkBox == null) {
                    final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                    checkBoxNew.setId(xcount);
                    DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(),inputName.getText().toString(),inputUnit.getText().toString(),spinner1.getSelectedItem().toString(),headid);
                    save.addList(dg3);
                    xcount+=1;
                    checkBoxNew.setText(checkBoxValue);
                    checcBoxFuncionality(Table,checkBoxNew,0);
                } else {
                        DyGroceriesList3 dg3 =  save.singleItem(checkBox.getId());
                        dg3.setName(inputName.getText().toString());
                        dg3.setUnit(inputUnit.getText().toString());
                        dg3.setListItem(spinner1.getSelectedItem().toString());
                        checkBox.setText(checkBoxValue);
                }



                popupWindow.dismiss();

            }
        });
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });

        //popupWindow.showAsDropDown(text1, 50, -30);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }


    protected  void TxtViewFuncionality(final TableLayout Table, final TextView TxtView, final int indicator){
        TxtView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1; i++) {
                    if (!TxtView.isClickable()) {
                        final int nIndex = Table.indexOfChild(TxtView);
                        Table.removeView(findViewById(100 + nIndex));

                    } else {
                        final int nIndex = Table.indexOfChild(TxtView);
                        TableRow row = new TableRow(getApplicationContext());


                        Table.setGravity(Gravity.CENTER);
                        //Table.setBackgroundResource(Color.MAGENTA);


                        final TextView text1 = new TextView(getApplicationContext());
                        TextView text2 = new TextView(getApplicationContext());
                        TextView text3 = new TextView(getApplicationContext());
                        row.setId(100 + nIndex);
                        text1.setText(" Edit ");
                        row.addView(text1);
                        text2.setText("Delete ");
                        row.addView(text2);
                        text3.setText("Cancel");
                        row.addView(text3);

                           /* Button btnTag1 = new Button(getApplicationContext());
                            btnTag1.setId(101+1);
                            row.addView(btnTag1);
                            ((Button) findViewById(101+1)).setOnClickListener(this);
                            Button btnTag2 = new Button(getApplicationContext());
                            btnTag2.setId(102);
                            row.addView(btnTag2);
                            ((Button) findViewById(102)).setOnClickListener(this);
                            Button btnTag3 = new Button(getApplicationContext());
                            btnTag3.setId(103);
                            row.addView(btnTag3);
                            ((Button) findViewById(103)).setOnClickListener(this);*/

                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (indicator == 0) {
                                  //  addItemsOnSpinner(TxtView, Table);
                                }else{
                                    //ArrayList<DyGroceriesList3> tmplist = save.fullListHead(checkBox.getId());
                                    //checkBoxList(checkBox.getText().toString(),tmplist);
                                }

                            }
                        });


                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Ištrinta prekė "+ TxtView.getText().toString(), Toast.LENGTH_LONG)
                                        .show();
                                int a = TxtView.getId();
                                save.removeItem(TxtView.getId());
                                Table.removeViewAt(nIndex);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                           /*     Toast.makeText(getApplicationContext(),
                                        "Cancel ME!!!", Toast.LENGTH_LONG)
                                        .show();*/
                                final int nIndex = Table.indexOfChild(TxtView);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });

                        Table.addView(row, nIndex + 1);
                    }
                           /* Toast.makeText(getApplicationContext(),
                                    "Position :" + checkBox.getId() + "  ListItem : " + checkBox.getText(), Toast.LENGTH_LONG)
                                    .show();*/

                    int moveckbox = TxtView.getId() + 1;


                }
            }
        });

        Table.addView(TxtView,0);

    }
protected  void checcBoxFuncionality(final TableLayout Table, final CheckBox checkBox, final int indicator){
        checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1; i++) {
                    if (!checkBox.isChecked()) {
                        final int nIndex = Table.indexOfChild(checkBox);
                        Table.removeView(findViewById(100 + nIndex));

                    } else {
                        final int nIndex = Table.indexOfChild(checkBox);
                        TableRow row = new TableRow(getApplicationContext());


                        Table.setGravity(Gravity.CENTER);
                        //Table.setBackgroundResource(Color.MAGENTA);


                        final TextView text1 = new TextView(getApplicationContext());
                        TextView text2 = new TextView(getApplicationContext());
                        TextView text3 = new TextView(getApplicationContext());
                        row.setId(100 + nIndex);
                        text1.setText(" Edit ");
                        row.addView(text1);
                        text2.setText("Delete ");
                        row.addView(text2);
                        text3.setText("Cancel");
                        row.addView(text3);

                           /* Button btnTag1 = new Button(getApplicationContext());
                            btnTag1.setId(101+1);
                            row.addView(btnTag1);
                            ((Button) findViewById(101+1)).setOnClickListener(this);
                            Button btnTag2 = new Button(getApplicationContext());
                            btnTag2.setId(102);
                            row.addView(btnTag2);
                            ((Button) findViewById(102)).setOnClickListener(this);
                            Button btnTag3 = new Button(getApplicationContext());
                            btnTag3.setId(103);
                            row.addView(btnTag3);
                            ((Button) findViewById(103)).setOnClickListener(this);*/

                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               if (indicator == 0) {
                                    addItemsOnSpinner(checkBox, Table);
                                }else{
                                  ArrayList<DyGroceriesList3> tmplist = save.fullListHead(checkBox.getId());
                                   checkBoxList(checkBox.getText().toString(),tmplist);
                                }

                            }
                        });


                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Ištrinta prekė "+ checkBox.getText().toString(), Toast.LENGTH_LONG)
                                        .show();
                                int a = checkBox.getId();
                                save.removeItem(checkBox.getId());
                                Table.removeViewAt(nIndex);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                           /*     Toast.makeText(getApplicationContext(),
                                        "Cancel ME!!!", Toast.LENGTH_LONG)
                                        .show();*/
                                final int nIndex = Table.indexOfChild(checkBox);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });

                        Table.addView(row, nIndex + 1);
                    }
                           /* Toast.makeText(getApplicationContext(),
                                    "Position :" + checkBox.getId() + "  ListItem : " + checkBox.getText(), Toast.LENGTH_LONG)
                                    .show();*/

                    int moveckbox = checkBox.getId() + 1;


                }
            }
        });

        Table.addView(checkBox,0);

}

protected void checkBoxList(String name,ArrayList<DyGroceriesList3> tmplist){

    setContentView(R.layout.singleitem);
    final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
    Button addCheckBox = (Button)findViewById(R.id.addCheckBox);
    Button backToMainMenu = (Button)findViewById(R.id.backToMainMenu);
    Button saveMyItems = (Button)findViewById(R.id.saveMyItems);

     final TextView titleView = (TextView)findViewById(R.id.titleName);
      titleView.setText(name);
    if (tmplist != null) {
        for (DyGroceriesList3 object : tmplist) {
            final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
            checkBoxNew.setId(object.getId());
            checkBoxNew.setText(object.getName());
            checcBoxFuncionality(Table,checkBoxNew,0);
        }
    }
    addCheckBox.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            addItemsOnSpinner(null,Table);

        }
    });
    backToMainMenu.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            setContentView(R.layout.activity_main);

            final TableLayout TableMain = (TableLayout) findViewById(R.id.myMainList);
            Button btnnewList = (Button) findViewById(R.id.newList);
            Button btnnewCategory = (Button) findViewById(R.id.newCategory);

            btnnewList.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    newList();
                }
            });

            btnnewCategory.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.categorylistm);
                    save.fullListCATG();
                }
            });



            final List<DyMealsList3> tmpheadlist = save.fullListDML3();

            for (DyMealsList3 object:tmpheadlist){
                    final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                    checkBoxNew.setId(object.getId());
                    checkBoxNew.setText(object.getName());
                    checcBoxFuncionality(TableMain,checkBoxNew,1);

            }
        }
    });

    saveMyItems.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            setContentView(R.layout.activity_main);

            final TableLayout TableMain = (TableLayout) findViewById(R.id.myMainList);
            mainListItem(null,TableMain,titleView.getText().toString(),headid);
            headid+=1;
            Button btnnewList = (Button) findViewById(R.id.newList);
            Button btnnewCategory = (Button) findViewById(R.id.newCategory);

                            btnnewList.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    newList();
                                }
                            });

                            btnnewCategory.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    setContentView(R.layout.categorylistm);
                                    final List<CategoryList> tmpCatlist = save.fullListCATG();
                                    final TableLayout tbname = (TableLayout) findViewById(R.id.cattbl);
                                    for (CategoryList object:tmpCatlist){
                                        TextView newCatItem = new TextView(getApplicationContext());
                                        newCatItem.setText(object.getName().toString());
                                         tbname.addView(newCatItem,0);
                                    }
                                }
                            });

            final List<DyMealsList3> tmpheadlist = save.fullListDML3();

            for (DyMealsList3 object:tmpheadlist){
                final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                checkBoxNew.setId(object.getId());
                checkBoxNew.setText(object.getName());
                checcBoxFuncionality(TableMain,checkBoxNew,1);

            }
        }
    });

}




protected void newList(){
     
    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    final View popupView1 = layoutInflater.inflate(R.layout.newlisti, null);
    final PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


    Button btnAcppectList = (Button) popupView1.findViewById(R.id.saveItemList);
    Button btnDismissList = (Button) popupView1.findViewById(R.id.dismissList);

    final EditText inputName = (EditText)popupView1.findViewById(R.id.itemNameList);


    btnAcppectList.setOnClickListener(new Button.OnClickListener(){
        @Override
        public void onClick(View v){


            checkBoxList(inputName.getText().toString(),null);
            popupWindow1.dismiss();

        }
    });

    btnDismissList.setOnClickListener(new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            popupWindow1.dismiss();

        }
    });


    popupWindow1.showAtLocation(popupView1, Gravity.CENTER, 0, 0);


}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnnewList = (Button) findViewById(R.id.newList);
        Button btnnewCategory = (Button) findViewById(R.id.newCategory);

        btnnewList.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                    newList();

            }
        });

        btnnewCategory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                    setContentView(R.layout.categorylistm);
                    final List<CategoryList> tmpCatlist = save.fullListCATG();
                    final TableLayout tbname = (TableLayout) findViewById(R.id.cattbl);
                    for (CategoryList object:tmpCatlist){
                        TextView newCatItem = new TextView(getApplicationContext());
                        newCatItem.setText(object.getName().toString());
                        TxtViewFuncionality(tbname,newCatItem,0);
                    }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



