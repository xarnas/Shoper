
package com.arnas.shoper;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    String checkBoxValue;

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

    public void addItemsOnSpinner(String checkBoxName, final TableLayout Table) {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = layoutInflater.inflate(R.layout.registry, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);




        final Spinner spinner1 = (Spinner) popupWindow.getContentView().findViewById(R.id.spinner1);

        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");


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
        //inputName.setText(checkBoxName);
        final EditText inputUnit = (EditText)popupView.findViewById(R.id.itemUnit);
        //inputUnit.setText(checkBoxName);
        spinner1.setSelection(2);

        if (!checkBoxName.isEmpty()) {
            String[] tokens = checkBoxName.split(" ");
            inputName.setText(tokens[0].toString());
            inputUnit.setText(tokens[1].toString());

            spinner1.setSelection(getIndex(spinner1, tokens[2].toString()));

        }

        btnAcppect.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //int z =1;
                if (inputName.getText().toString().isEmpty() || inputUnit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Užpildykite visus laukus", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                checkBoxValue=inputName.getText().toString()+" "+inputUnit.getText().toString()+" "+spinner1.getSelectedItem().toString();
                final CheckBox checkBox = new CheckBox(getApplicationContext());
                checkBox.setText(checkBoxValue);
               // checkBox.setId(10+z);
                checcBoxFuncionality(Table,checkBox);
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
protected  void checcBoxFuncionality(final TableLayout Table,final CheckBox checkBox){
    int z =1;
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

                                addItemsOnSpinner(checkBox.getText().toString(),Table);


                            }

                        });


                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Ištrinta prekė "+ checkBox.getText().toString(), Toast.LENGTH_LONG)
                                        .show();
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
    z++;
}

protected void checkBoxList(){

    setContentView(R.layout.singleitem);
    final TableLayout Table = (TableLayout) findViewById(R.id.tablein);

    Button addCheckBox = (Button)findViewById(R.id.addCheckBox);



    addCheckBox.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            addItemsOnSpinner("",Table);

        }

    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //   .setAction("Action", null).show()

               // values[0]="Ramune";
                // listView.invalidateViews();

                checkBoxList();
            }
        });

        values[0]="Arnas";
        this.listView = (ListView)findViewById(R.id.list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, this.values);

            this.listView.setAdapter(adapter);

            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    int itemPosition = position;
                    String itemValue = (String) listView.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();


                    checkBoxList();

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



