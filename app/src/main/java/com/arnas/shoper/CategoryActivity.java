package com.arnas.shoper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import java.util.List;

/**
 * Created by arnaspetrauskas on 08/09/2017.
 */

public class CategoryActivity extends AppCompatActivity {

    SaveList save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        save = (SaveList) getIntent().getParcelableExtra("SAVE_OBJECT");
        //name = getIntent().getStringExtra("NEW_ITEM").toString();
        setContentView(R.layout.categorylistm);

        final List<CategoryList> tmpCatlist = save.fullListCATG();
        final TableLayout tbname = (TableLayout) findViewById(R.id.cattbl);

        for (CategoryList object:tmpCatlist){
            TextView newCatItem = new TextView(getApplicationContext());
            newCatItem.setText(object.getName().toString());
            TxtViewFuncionality(tbname,newCatItem,0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_categ, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.backToMainMenu2) {
            Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.newCategoryItem) {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupViewCat = layoutInflater.inflate(R.layout.category, null);
        final PopupWindow popupWindowCat = new PopupWindow(popupViewCat, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        Button btnAcppect = (Button) popupViewCat.findViewById(R.id.saveitem);
        Button btnDismiss = (Button) popupViewCat.findViewById(R.id.dismiss);
        final EditText inputName = (EditText)popupViewCat.findViewById(R.id.itemName);
            btnDismiss.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                popupWindowCat.dismiss();
            }});
        btnAcppect.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View V) {
            final TableLayout tbname = (TableLayout) findViewById(R.id.cattbl);
            CategoryList cat = new CategoryList(save.getCatid() + 1, inputName.getText().toString());
            save.addCategory(cat);
            save.setCatid(save.getCatid() + 1);
            String addedItem = save.getCatid()+":"+inputName.getText().toString()+";";
            FileWrite("Category",addedItem);
            TextView newCatItem = new TextView(getApplicationContext());
            newCatItem.setText(inputName.getText().toString());
            TxtViewFuncionality(tbname, newCatItem, 0);
            popupWindowCat.dismiss();
        }});
             popupWindowCat.showAtLocation(popupViewCat, Gravity.CENTER, 0, 0);

        }

        return super.onOptionsItemSelected(item);

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
            }});
        btnAcppect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View V) {
            String UpdateCatList="";
            for (CategoryList object : save.fullListCATG()) {
            if (object.getName().equals(TxtView.getText().toString())) {
            object.setName(inputName.getText().toString());
            save.UpdateCatItem(object.getId() - 1, object);
            TxtView.setText(inputName.getText().toString());
                String catRawList = FileRead("Category");
                String[] myCat = catRawList.split(";");
                for (String t : myCat) {
                    String[] token = t.split(":");
                    if (!token[0].isEmpty() && !token[0].contains("\n")){
                         if(Integer.parseInt(token[0])!= object.getId()) {
                             UpdateCatList = UpdateCatList + token[0] + ":" + token[1] + ";";
                         }else{
                             int catidupdate = object.getId();
                             UpdateCatList = UpdateCatList + catidupdate+ ":" + object.getName().toString() + ";";
                         }
                    }}
            popupWindowCat.dismiss();
            }
            }
            ClearFile("Category");
            FileWrite("Category",UpdateCatList);
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
        save.removeItemCat(TxtView.getId());
        String UpdateCatList="";
        String catRawList = FileRead("Category");
        String[] myCat = catRawList.split(";");
        for (String t : myCat) {
        String[] token = t.split(":");
        if (!token[0].isEmpty() && !token[0].contains("\n")){
            if(Integer.parseInt(token[0])!= TxtView.getId()) {
                UpdateCatList = UpdateCatList + token[0] + ":" + token[1] + ";";
            }
        }}
        ClearFile("Category");
        FileWrite("Category",UpdateCatList);
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

}
