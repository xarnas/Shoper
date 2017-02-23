package com.arnas.shoper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by arnaspetrauskas on 13/02/2017.
 */

public class DyGroceriesList3 extends MainActivity {

    protected void checkBoxList(){

        final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
        Button addCheckBox = (Button)findViewById(R.id.addCheckBox);
        final EditText singleItem= (EditText) findViewById(R.id.itemName2);
       // final TextView headItem= (TextView) findViewById(R.id.itemName);


        addCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(int i=0;i<1;i++) {
                    final CheckBox checkBox = new CheckBox(getApplicationContext());
                    if (singleItem.getText().toString().isEmpty()){
                        break;
                    }else {

                        checkBox.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {

                                if (!checkBox.isChecked()){
                                    final int nIndex = Table.indexOfChild(checkBox);
                                    Table.removeView(findViewById(100+nIndex));
                                }else {
                                    final int nIndex = Table.indexOfChild(checkBox);
                                    TableRow row = new TableRow(getApplicationContext());


                                    Table.setGravity(Gravity.CENTER);

                                    TextView text1 = new TextView(getApplicationContext());
                                    TextView text2 = new TextView(getApplicationContext());
                                    TextView text3 = new TextView(getApplicationContext());

                                    row.setId(100 + nIndex);
                                    text1.setText(" Edit ");
                                    row.addView(text1);
                                    text2.setText("Delete ");
                                    row.addView(text2);
                                    text3.setText("Cancel");
                                    row.addView(text3);


                                    text1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            checkBox.setText("CHANGED!");
                                            Toast.makeText(getApplicationContext(),
                                                    "Edit ME!!!", Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                                    text2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Table.removeViewAt(nIndex);
                                            Table.removeView(findViewById(100+nIndex));
                                            Toast.makeText(getApplicationContext(),
                                                    "Delete ME!!!", Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                                    text3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Cancel ME!!!", Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });

                                    Table.addView(row,nIndex+1);
                                }

                            }

                        });

                        Table.addView(checkBox,0);
                    }

                }
            }

        });
    }
}
