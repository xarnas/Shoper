
package com.arnas.shoper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    ListView listView ;
    String[] values= new String[1];

protected void checkBoxList(){
    setContentView(R.layout.singleitem);
    /*final LinearLayout linear=(LinearLayout)findViewById(R.id.singleitem);*/
    final TableLayout Table = (TableLayout) findViewById(R.id.tablein);
    Button addCheckBox = (Button)findViewById(R.id.addCheckBox);
    final EditText singleItem= (EditText) findViewById(R.id.itemName2);
    final TextView headItem= (TextView) findViewById(R.id.itemName);
   /* final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);*/

    addCheckBox.setOnClickListener(new View.OnClickListener() {
        int z =1;
        @Override
        public void onClick(View v) {

            for(int i=0;i<1;i++) {

                final CheckBox checkBox = new CheckBox(getApplicationContext());
                if (singleItem.getText().toString().isEmpty()){
                    break;
                }else {
                    checkBox.setText(singleItem.getText());
                    headItem.setText("New checkbox inserted");
                   /* checkBox.setLayoutParams(lparams);*/
                    checkBox.setId(10+z);



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
                                //Table.setBackgroundResource(Color.MAGENTA);


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
                           /* Toast.makeText(getApplicationContext(),
                                    "Position :" + checkBox.getId() + "  ListItem : " + checkBox.getText(), Toast.LENGTH_LONG)
                                    .show();*/
                              int moveckbox = checkBox.getId()+1;


                       /*    if (checkBox.isChecked()){
                               CheckBox cbx = (CheckBox) findViewById(moveckbox);
                               cbx.setY(cbx.getY()+100);
                            //Button btn = new Button(getApplicationContext());
                                int a=30;
                               for (int i = 1; i < 4; i++) {

                                   /*linear.removeView(findViewById(100+i));*/
                         /*          Button btnTag = new Button(getApplicationContext());
                                  /* btnTag.setLayoutParams(lparams);*/

                       /*            switch (i) {
                                       case 1:
                                           btnTag.setText("Edit"+i);
                                           break;
                                       case 2:
                                           btnTag.setText("Delete"+i);
                                           break;
                                       case 3:
                                           btnTag.setText("Cancel"+i);
                                           break;
                                   }
                                   btnTag.setX(cbx.getX()+10+a);
                                   //btnTag.setY(cbx.getY());
                                   btnTag.setId(100+i);
                                   Table.addView(btnTag);
                                   ((Button) findViewById(100+i)).setOnClickListener(this);
                                   a=+30;
                               }*/



                           }

                    });

                    Table.addView(checkBox,0);
                }
                z++;
            }
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



