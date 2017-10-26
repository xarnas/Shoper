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

import org.w3c.dom.Text;

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
    dbSQL helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         save = (SaveList) getIntent().getParcelableExtra("SAVE_OBJECT");
         name = getIntent().getStringExtra("NEW_ITEM").toString();
         checkBoxList(name,null);
          helper = new dbSQL(this);


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
            helper.deleteGros(helper.getLastOpenId()+1);
            startActivity(intent);
        }
        if (id == R.id.addCheckBox) {
            final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
            addItemsOnSpinner(null,Table,helper.getLastOpenId());
        }
        if (id == R.id.saveMyItems) {
            final TextView titleView = (TextView)findViewById(R.id.titleName);
            mainListItem(null,titleView.getText().toString(),helper.getLastOpenId()+1);
        }
        return super.onOptionsItemSelected(item);

    }

    protected void checkBoxList(String name,ArrayList<DyGroceriesList3> tmplist){

        setContentView(R.layout.singleitem);

        final TextView titleView = (TextView)findViewById(R.id.titleName);
        final TableLayout Table = (TableLayout) findViewById(R.id.tablein);

        titleView.setText(name);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView1 = layoutInflater.inflate(R.layout.newlisti, null);
                final PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                Button btnAcppectList = (Button) popupView1.findViewById(R.id.saveItemList);
                Button btnDismissList = (Button) popupView1.findViewById(R.id.dismissList);
                final EditText inputName = (EditText)popupView1.findViewById(R.id.itemNameList);
                inputName.setText(titleView.getText());
                btnAcppectList.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        titleView.setText(inputName.getText());
                        titleView.setTypeface(null, Typeface.BOLD);
                        //String DM3 = FileRead("MainMenu");
                        //String Changer = save.changeMainMenuTitle(openHeadId,DM3,inputName.getText().toString());
                        helper.updateSingleMainMenu(openHeadId,inputName.getText().toString());
                        //ClearFile("MainMenu");
                        //FileWrite("MainMenu", Changer);
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
        });

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
                        text2.setText(" ištrinti ");
                        row.addView(text2);
                        text3.setText(" atšaukti");
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


                                save.removeItem(checkBox.getId(), openHeadId,helper);

                                Table.removeViewAt(nIndex);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int nIndex = Table.indexOfChild(checkBox);
                                Table.removeView(findViewById(100 + nIndex));
                            }
                        });

                        Table.addView(row, nIndex + 1);
                    }

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
        //final EditText inputPrice = (EditText) popupView.findViewById(R.id.itemPrice);
        final TextView openDetal = (TextView) popupView.findViewById(R.id.openDetails);
        float openPrice=0;
        final float[] newOpenPrice = {0};
        //inputPrice.setText("0");

        spinner1.setSelection(0);

        // if (!checkBox.getText().toString().isEmpty()) {
        if (checkBox != null) {
            DyGroceriesList3 dgl3 = save.singleItem(checkBox.getId(), openHeadId);
            inputName.setText(dgl3.getName().toString());
            inputUnit.setText(dgl3.getUnit().toString());

            spinner1.setSelection(getIndex(spinner1, dgl3.getListItem().toString()));

            //Pricer integration
            Pricer prc = save.singleItemPrice(checkBox.getId(), openHeadId);
            openPrice =prc.getPrice();
            //TODO Picture implentation
        }

        final float finalOpenPrice = openPrice;
        openDetal.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.registryoption, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


                final Spinner spinner1 = (Spinner) popupWindow.getContentView().findViewById(R.id.pricer);
                //Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                TextView btnDismiss = (TextView) popupView.findViewById(R.id.dismiss);

                final EditText inputPrice = (EditText) popupView.findViewById(R.id.itemPrice);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        newOpenPrice[0] = finalOpenPrice;
                        popupWindow.dismiss();
                    }
                });
                if (finalOpenPrice != 0){
                    inputPrice.setText(String.valueOf(finalOpenPrice));
                }
                else {
                    inputPrice.setText("0");
                }
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);



            }
        });
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
                        DyGroceriesList3 dg3 = new DyGroceriesList3(checkBoxNew.getId(), inputName.getText().toString(), inputUnit.getText().toString(), spinner1.getSelectedItem().toString(), MainHeadId + 1,0);
                        Pricer prc= new Pricer(MainHeadId+1,groceriesCount,newOpenPrice[0],"","");
                        //FileWrite("Groceries", inputName.getText().toString() + ":" + inputUnit.getText().toString() + ":" + spinner1.getSelectedItem().toString() + ":" + String.valueOf(groceriesCount) + ":" + String.valueOf(MainHeadId + 1) + ";");
                        //FileWrite("Pricer", String.valueOf(MainHeadId+1)+":"+String.valueOf(groceriesCount)+":"+String.valueOf(newOpenPrice[0])+";");
                        save.addList(dg3);
                        save.addPriceToItem(prc);
                        helper.insertDataGroceries(inputName.getText().toString(),MainHeadId+1,inputUnit.getText().toString(),spinner1.getSelectedItem().toString(),newOpenPrice[0],"","");
                    checkBoxNew.setText(checkBoxValue);
                    checcBoxFuncionality(Table, checkBoxNew, 0);
                } else {
                    DyGroceriesList3 dg3 = save.singleItem(checkBox.getId(), openHeadId);
                    dg3.setName(inputName.getText().toString());
                    dg3.setUnit(inputUnit.getText().toString());
                    dg3.setListItem(spinner1.getSelectedItem().toString());
                    checkBox.setText(checkBoxValue);
                    helper.updateSingleGroceries(checkBox.getId(),MainHeadId+1,inputName.getText().toString(),inputUnit.getText().toString(),spinner1.getSelectedItem().toString(),newOpenPrice[0],"","");

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
            //String tmpsumname = Course + ":" + String.valueOf(headid) + ";";
            helper.insertDataMainMenu(Course);

            //FileWrite("MainMenu", tmpsumname);
            //save.setLastID(headid);
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
