
package com.arnas.shoper;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.SyncStateContract;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] values = new String[1];
    SaveList save = new SaveList();
    FileReadWrite frw = new FileReadWrite();
    String checkBoxValue;
    int xcount = 0;
    int openHeadId = 0;
    boolean editMode = false;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;




    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }



    public void mainListItem(final CheckBox checkBox, final TableLayout Table, String Course, int headid) {
        checkBoxValue = Course;
        if (checkBox == null) {
            DyMealsList3 DML3 = new DyMealsList3(headid, Course);
            save.addListDML3(DML3);
            String tmpsumname = Course + ":" + String.valueOf(headid) + ";";
            FileWrite("MainMenu", tmpsumname);
            save.setLastID(headid);
        }
    }


    public void addItemsOnSpinner(final CheckBox checkBox, final TableLayout Table, final int MainHeadId) {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = layoutInflater.inflate(R.layout.registry, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        final Spinner spinner1 = (Spinner) popupWindow.getContentView().findViewById(R.id.spinner1);

        List<String> list = new ArrayList<String>();
        final List<CategoryList> tmpCatlist = save.fullListCATG();

        for (CategoryList object : tmpCatlist) {
            list.add(object.getName().toString());
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Toast.makeText(getBaseContext(), "Listiner test "+spinner1.getId(), Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        spinner1.setAdapter(dataAdapter);

        Button btnAcppect = (Button) popupView.findViewById(R.id.saveitem);
        Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
        final EditText inputName = (EditText) popupView.findViewById(R.id.itemName);
        final EditText inputUnit = (EditText) popupView.findViewById(R.id.itemUnit);

        spinner1.setSelection(2);

        // if (!checkBox.getText().toString().isEmpty()) {
        if (checkBox != null) {
            DyGroceriesList3 dgl3 = save.singleItem(checkBox.getId(), openHeadId);
            inputName.setText(dgl3.getName().toString());
            inputUnit.setText(dgl3.getUnit().toString());

            spinner1.setSelection(getIndex(spinner1, dgl3.getListItem().toString()));

        }

        btnAcppect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputName.getText().toString().isEmpty() || inputUnit.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Užpildykite visus laukus", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                checkBoxValue = inputName.getText().toString() + " " + inputUnit.getText().toString() + " " + spinner1.getSelectedItem().toString();

                if (checkBox == null) {
                    final CheckBox checkBoxNew = new CheckBox(getApplicationContext());

                    if (editMode == true) {
                        int groceriesCount = save.GroceriesLastId(openHeadId) + 1;
                        checkBoxNew.setId(groceriesCount);
                        DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(), inputName.getText().toString(), inputUnit.getText().toString(), spinner1.getSelectedItem().toString(), openHeadId);
                        FileWrite("Groceries", inputName.getText().toString() + ":" + inputUnit.getText().toString() + ":" + spinner1.getSelectedItem().toString() + ":" + String.valueOf(groceriesCount) + ":" + String.valueOf(openHeadId) + ";");
                        save.addList(dg3);
                    } else {
                        int groceriesCount = save.GroceriesLastId(MainHeadId + 1) + 1;
                        checkBoxNew.setId(groceriesCount);
                        DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(), inputName.getText().toString(), inputUnit.getText().toString(), spinner1.getSelectedItem().toString(), MainHeadId + 1);
                        FileWrite("Groceries", inputName.getText().toString() + ":" + inputUnit.getText().toString() + ":" + spinner1.getSelectedItem().toString() + ":" + String.valueOf(groceriesCount) + ":" + String.valueOf(MainHeadId + 1) + ";");
                        save.addList(dg3);
                    }


                    checkBoxNew.setText(checkBoxValue);
                    checcBoxFuncionality(Table, checkBoxNew, 0);
                } else {
                    DyGroceriesList3 dg3 = save.singleItem(checkBox.getId(), openHeadId);
                    dg3.setName(inputName.getText().toString());
                    dg3.setUnit(inputUnit.getText().toString());
                    dg3.setListItem(spinner1.getSelectedItem().toString());
                    checkBox.setText(checkBoxValue);
                    String[] tokens = FileRead("Groceries").split(";");
                    String tmpfilestr = "";
                    String tmpfilestr2 = "";
                    boolean reWrite = false;
                    for (String t : tokens) {
                        String[] tokens2 = t.split(":");
                        if (!tokens2[0].contains("\n") && !tokens2.toString().isEmpty()) {
                            if (tokens2[3].equals(String.valueOf(checkBox.getId())) && tokens2[4].equals(String.valueOf(openHeadId))) {
                                if (!tokens2[0].equals(inputName.getText().toString())
                                        || !tokens2[1].equals(inputName.getText().toString())
                                        || !tokens[2].equals(inputName.getText().toString())) {
                                    tmpfilestr2 = inputName.getText().toString() + ":" + inputUnit.getText().toString() + ":" + spinner1.getSelectedItem().toString() + ":" + String.valueOf(dg3.getId()) + ":" + String.valueOf(dg3.getHeadId()) + ";";
                                    tmpfilestr += tmpfilestr2;
                                    reWrite = true;
                                } else {
                                    tmpfilestr += t.toString() + ";";
                                }
                            } else {
                                tmpfilestr += t.toString() + ";";
                            }
                        }


                        if (reWrite == true) {
                            ClearFile("Groceries");
                            FileWrite("Groceries", tmpfilestr);
                        }


                    }

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


    protected void TxtViewFuncionality(final TableLayout Table, final TextView TxtView, final int indicator) {


        TxtView.setOnClickListener(new View.OnClickListener() {
            boolean lock = false;

            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1; i++) {
                    //if (!TxtView.isClickable()) {
                    //  final int nIndex = Table.indexOfChild(TxtView);
                    //  Table.removeView(findViewById(100 + nIndex));

                    //} else {
                    if (!lock) {
                        lock = true;
                        final int nIndex = Table.indexOfChild(TxtView);
                        TableRow row = new TableRow(getApplicationContext());


                        Table.setGravity(Gravity.CENTER);
                        //Table.setBackgroundResource(Color.MAGENTA);


                        final TextView text1 = new TextView(getApplicationContext());
                        TextView text2 = new TextView(getApplicationContext());
                        TextView text3 = new TextView(getApplicationContext());
                        row.setId(100 + nIndex);
                        text1.setText(" keisti ");
                        row.addView(text1);
                        text2.setText("ištrinti ");
                        row.addView(text2);
                        text3.setText("atšaukti");
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

                                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupViewCat = layoutInflater.inflate(R.layout.category, null);
                                final PopupWindow popupWindowCat = new PopupWindow(popupViewCat, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                Button btnAcppect = (Button) popupViewCat.findViewById(R.id.saveitem);
                                Button btnDismiss = (Button) popupViewCat.findViewById(R.id.dismiss);
                                final EditText inputName = (EditText) popupViewCat.findViewById(R.id.itemName);
                                inputName.setText(TxtView.getText().toString());
                                btnDismiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View V) {
                                        popupWindowCat.dismiss();
                                    }
                                });
                                btnAcppect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View V) {
                                        for (CategoryList object : save.fullListCATG()) {
                                            if (object.getName().equals(TxtView.getText().toString())) {
                                                object.setName(inputName.getText().toString());
                                                save.UpdateCatItem(object.getId() - 1, object);
                                                TxtView.setText(inputName.getText().toString());
                                                popupWindowCat.dismiss();
                                            }
                                        }

                                    }
                                });


                                popupWindowCat.showAtLocation(popupViewCat, Gravity.CENTER, 0, 0);


                            }
                        });


                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Ištrinta kategorija " + TxtView.getText().toString(), Toast.LENGTH_LONG)
                                        .show();
                                int a = TxtView.getId();
                                save.removeItemCat(TxtView.getId());
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
                                lock = false;
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

        Table.addView(TxtView, 0);

    }

    protected void checcBoxFuncionality(final TableLayout Table, final CheckBox checkBox, final int indicator) {
        checkBox.setOnClickListener(new View.OnClickListener() {

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
                        text1.setText("  keisti ");
                        row.addView(text1);
                        text2.setText("  ištrinti ");
                        row.addView(text2);
                        text3.setText("  atšaukti");
                        row.addView(text3);

                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (indicator == 0) {
                                    addItemsOnSpinner(checkBox, Table, checkBox.getId());
                                } else {
                                   // ArrayList<DyGroceriesList3> tmplist = (ArrayList<DyGroceriesList3>) save.fullListHead(checkBox.getId());
                                    openHeadId = checkBox.getId();
                                    Intent intent = new Intent(MainActivity.this, editListActivity.class);
                                    intent.putExtra("NEW_ITEM",  checkBox.getText().toString());
                                    intent.putExtra("SAVE_OBJECT", (Parcelable) save);
                                    intent.putExtra("GROCERIES_ID",checkBox.getId());
                                    startActivity(intent);
                                    //checkBoxList(checkBox.getText().toString(), tmplist);
                                }

                            }
                        });


                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupViewCat = layoutInflater.inflate(R.layout.message, null);
                                final PopupWindow popupWindowCat = new PopupWindow(popupViewCat, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                Button btnAcppect = (Button) popupViewCat.findViewById(R.id.deleteItem);
                                Button btnDismiss = (Button) popupViewCat.findViewById(R.id.dismiss);
                                final TextView inputName = (TextView)popupViewCat.findViewById(R.id.deleteItemText);
                                inputName.setText("Ar norite ištrinti "+checkBox.getText().toString().toUpperCase()+" ?");
                                btnDismiss.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View V){
                                        popupWindowCat.dismiss();
                                    }
                                });

                                btnAcppect.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                        public void onClick(View V){
                                            Toast.makeText(getApplicationContext(),
                                                    "Ištrinta iš sąrašo " + checkBox.getText().toString(), Toast.LENGTH_LONG)
                                                    .show();
                                            String DM3 = FileRead("MainMenu");
                                            String GR3 = FileRead("Groceries");
                                            String modiList = save.removeItem(checkBox.getId(), DM3, GR3);
                                            String[] modiListsplited = modiList.split("##");
                                            ClearFile("Groceries");
                                            FileWrite("Groceries", modiListsplited[1].toString());
                                            ClearFile("MainMenu");
                                            FileWrite("MainMenu", modiListsplited[0].toString());

                                            Table.removeViewAt(nIndex);
                                            Table.removeView(findViewById(100 + nIndex));
                                            popupWindowCat.dismiss();
                                        }
                                    });
                             popupWindowCat.showAtLocation(popupViewCat, Gravity.CENTER, 0, 0);

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

        Table.addView(checkBox, 0);

    }


protected void checkBoxList(String name,ArrayList<DyGroceriesList3> tmplist){

    setContentView(R.layout.singleitem);

    final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
    Button addCheckBox = (Button)findViewById(R.id.addCheckBox);
    Button backToMainMenu = (Button)findViewById(R.id.backToMainMenu);
    Button saveMyItems = (Button)findViewById(R.id.saveMyItems);

     final TextView titleView = (TextView)findViewById(R.id.titleName);
      titleView.setText(name);
      titleView.setTypeface(null,Typeface.BOLD);
    if (tmplist != null) {
        for (DyGroceriesList3 object : tmplist) {
            final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
            checkBoxNew.setId(object.getId());
            checkBoxNew.setText(object.getName()+" "+object.getUnit()+" "+object.getListItem());
            checkBoxNew.setTypeface(null,Typeface.BOLD_ITALIC);
            checcBoxFuncionality(Table,checkBoxNew,0);

        }
    }
    addCheckBox.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            addItemsOnSpinner(null,Table,save.getLastID());

        }
    });

    saveMyItems.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
           // FileWrite("MainMenu",titleView.getText()+":"+headid);
            setContentView(R.layout.activity_main);

            final TableLayout TableMain = (TableLayout) findViewById(R.id.myMainList);


            if (editMode == false) {
            mainListItem(null,TableMain,titleView.getText().toString(),save.getLastID()+1);
           //save.setLastID(save.getLastID()+1);
            }

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
            editMode=false;
            xcount=0;
        }
    });

}




public void newList(){
     
    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    final View popupView1 = layoutInflater.inflate(R.layout.newlisti, null);
    final PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    Button btnAcppectList = (Button) popupView1.findViewById(R.id.saveItemList);
    Button btnDismissList = (Button) popupView1.findViewById(R.id.dismissList);
    final EditText inputName = (EditText)popupView1.findViewById(R.id.itemNameList);


    btnAcppectList.setOnClickListener(new Button.OnClickListener(){
        @Override
        public void onClick(View v){

            Intent intent = new Intent(MainActivity.this, newListActivity.class);
            intent.putExtra("NEW_ITEM",  inputName.getText().toString());
            intent.putExtra("SAVE_OBJECT", (Parcelable) save);
            startActivity(intent);

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

    // File Read,Write and Clear functions
    public void FileWrite(String fileName,String Message) {

        //String Message = "Arnas Test";

        //tring fileName ="MainMenu";

        try{
            FileOutputStream fileOutputStream = this.openFileOutput(fileName,MODE_APPEND);
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String FileRead(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        String Message;
        try{

            FileInputStream fileInputStream = this.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((Message=bufferedReader.readLine())!=null){
                stringBuilder.append(Message+"\n");
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }



    public void ClearFile(String fileName){
        String Message = "";
        try{
            FileOutputStream fileOutputStream = this.openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // end of Read, Write Clear function

    //Loading full list from txt file
    public void LoadFullListFromFile(String file){
        //ClearFile("MainMenu");
        //ClearFile("Groceries");
         save.DyGroceriesList3Clear();
         save.DyMealsList3Clear();
        final TableLayout Table = (TableLayout) findViewById(R.id.myMainList);
        String fullList = FileRead(file);
        String[] tokens = fullList.split(";");
        // ClearFile("MainMenu");
        int headIdmain =0;
        for (String t : tokens) {

            String[] tokens2 = t.split(":");
            if (!tokens2[0].isEmpty() && !tokens2[0].contains("\n"))  {
                final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                headIdmain=Integer.parseInt(tokens2[1]);
                checkBoxNew.setId(headIdmain);
                checkBoxNew.setText(tokens2[0]);
                checkBoxNew.setTypeface(null, Typeface.BOLD);
                DyMealsList3 DML3 = new DyMealsList3(headIdmain, tokens2[0]);
                save.addListDML3(DML3);
                checcBoxFuncionality(Table, checkBoxNew, 1);
                save.setLastID(headIdmain);
            }}
            String fullListGR = FileRead("Groceries");
            String[] tokensGR = fullListGR.split(";");
            for (String t : tokensGR) {
            String[] tokens2 = t.split(":");
            if (!tokens2[0].isEmpty() && !tokens2[0].contains("\n"))  {

                DyGroceriesList3 DGL3 = new DyGroceriesList3(Integer.parseInt(tokens2[3].toString()),tokens2[0].toString(),tokens2[1].toString(),tokens2[2].toString(),Integer.parseInt(tokens2[4].toString()));
                if (tokens2.length == 6) {
                    DGL3.setActiveStatus(Integer.parseInt(tokens2[5]));
                }
                save.addList(DGL3);

            }
        }


    }
//end of Load from file function
public void CreateCategory(){

    /*String[] myCat = {"Pieno gaminiai ir kiaušiniai",
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
    };*/
      int catId=0;
      String catRawList = FileRead("Category");
      String[] myCat = catRawList.split(";");
      for (String t : myCat) {
        String[] token = t.split(":");
        if (!token[0].isEmpty() && !token[0].contains("\n")) {
            catId=Integer.parseInt(token[0]);
            CategoryList ct1 = new CategoryList(catId, token[1]);
            save.addCategory(ct1);
            save.setCatid(catId);
        }}



    //FileWrite("Category",catfulllist);

}
public String generateShopingList(){

    String myShopCart="";

    TableLayout layout = (TableLayout) findViewById(R.id.myMainList);
    for (int i = 0; i < layout.getChildCount(); i++) {
        View child = layout.getChildAt(i);
        if (child instanceof CheckBox) {
            CheckBox cb = (CheckBox)child;
            if (cb.isChecked()){
                myShopCart+=cb.getId()+";";
            }
            //Toast.makeText(getBaseContext(),   cb.getText().toString()+" "+cb.isChecked(), Toast.LENGTH_SHORT).show();

        }
    }
    //shooperList(myShopCart); temp removed
    return myShopCart;

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ClearFile("Groceries"); //for data wipe
        //ClearFile("MainMenu"); // for data wipe
        //ClearFile("Category"); // for data wipe
        setContentView(R.layout.activity_main);
        CreateCategory();
        LoadFullListFromFile("MainMenu");
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
        if (id == R.id.generList) {
            if (generateShopingList().isEmpty()){
                Toast.makeText(getBaseContext(), "Pasirinkite iš sąrašo", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Intent intent = new Intent(MainActivity.this, generateShopList.class);
                intent.putExtra("GENERATED_SHOP_LIST",  generateShopingList());
                intent.putExtra("SAVE_OBJECT", (Parcelable) save);
                startActivity(intent);
            }
        }
        if (id == R.id.newList) {
            newList();
        }
        if (id == R.id.newCategory) {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("SAVE_OBJECT", (Parcelable) save);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}



