package com.arnas.shoper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 08/09/2017.
 */

public class newListActivity extends AppCompatActivity {

    String name;
    SaveList save;
    boolean editMode;
    int openHeadId;
    String checkBoxValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         save = (SaveList) getIntent().getParcelableExtra("SAVE_OBJECT");
         name = getIntent().getStringExtra("NEW_ITEM").toString();
         checkBoxList(name,null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_singleitem, menu);
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
            Intent intent = new Intent(newListActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.addCheckBox) {
            final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
            openHeadId=save.getLastID()+1;
            addItemsOnSpinner(null,Table,save.getLastID());
        }
        if (id == R.id.saveMyItems) {
            final TextView titleView = (TextView)findViewById(R.id.titleName);
            mainListItem(null,titleView.getText().toString(),save.getLastID()+1);
        }
        return super.onOptionsItemSelected(item);

    }

    protected void checkBoxList(String name,ArrayList<DyGroceriesList3> tmplist){

        setContentView(R.layout.singleitem);

        final TextView titleView = (TextView)findViewById(R.id.titleName);
        final TableLayout Table = (TableLayout) findViewById(R.id.tablein);

        titleView.setText(name);
        titleView.setTypeface(null, Typeface.BOLD);
        if (tmplist != null) {
            for (DyGroceriesList3 object : tmplist) {
                final CheckBox checkBoxNew = new CheckBox(getApplicationContext());
                checkBoxNew.setId(object.getId());
                checkBoxNew.setText(object.getName()+" "+object.getUnit()+" "+object.getListItem());
                checkBoxNew.setTypeface(null,Typeface.BOLD_ITALIC);
                checcBoxFuncionality(Table,checkBoxNew,0);

            }
        }
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
                        text1.setText(" keisti ");
                        row.addView(text1);
                        text2.setText("ištrinti ");
                        row.addView(text2);
                        text3.setText("atšaukti");
                        row.addView(text3);


                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    addItemsOnSpinner(checkBox, Table, checkBox.getId());
                            }
                        });

                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Ištrinta prekė " + checkBox.getText().toString(), Toast.LENGTH_LONG)
                                        .show();
                                int a = checkBox.getId();
                                if (editMode) {
                                    save.removeItem(checkBox.getId(), openHeadId);
                                } else {
                                    //save.removeItem(checkBox.getId());
                                }
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

        Table.addView(checkBox, 0);

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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(newListActivity.this, android.R.layout.simple_spinner_item, list);

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
                        int groceriesCount = save.GroceriesLastId(MainHeadId + 1) + 1;
                        checkBoxNew.setId(groceriesCount);
                        DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(), inputName.getText().toString(), inputUnit.getText().toString(), spinner1.getSelectedItem().toString(), MainHeadId + 1);
                        FileWrite("Groceries", inputName.getText().toString() + ":" + inputUnit.getText().toString() + ":" + spinner1.getSelectedItem().toString() + ":" + String.valueOf(groceriesCount) + ":" + String.valueOf(MainHeadId + 1) + ";");
                        save.addList(dg3);
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
    public void mainListItem(final CheckBox checkBox, String Course, int headid) {
        checkBoxValue = Course;
        if (checkBox == null) {
            DyMealsList3 DML3 = new DyMealsList3(headid, Course);
            save.addListDML3(DML3);
            String tmpsumname = Course + ":" + String.valueOf(headid) + ";";
            FileWrite("MainMenu", tmpsumname);
            save.setLastID(headid);
            Intent intent = new Intent(newListActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
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
}
