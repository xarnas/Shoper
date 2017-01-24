package com.arnas.shoper;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by arnaspetrauskas on 24/01/2017.
 */

public class DynamicSingleItem extends AppCompatActivity {


    public DynamicSingleItem(int CView) {
        switch(CView){
            case 1:
                break;
            case 2:
                this.CreateCheckBoxList();
                break;
        }

    }


private void CreateCheckBoxList(){
    setContentView(R.layout.singleitem);
    final LinearLayout linear=(LinearLayout)findViewById(R.id.singleitem);
    Button addCheckBox = (Button)findViewById(R.id.addCheckBox);

    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    addCheckBox.setOnClickListener(new View.OnClickListener() {
        int z =1;
        @Override
        public void onClick(View v) {

            for(int i=0;i<1;i++) {

                CheckBox checkBox = new CheckBox(getApplicationContext());
                checkBox.setText("I'm dynamic! "+z);
                checkBox.setLayoutParams(lparams);

                linear.addView(checkBox);
                z++;
            }
        }

    });
}
private void CreateView(int create){

    switch (create){
        case 2:
            setContentView(R.layout.singleitem);
            break;
        case 1:
            setContentView(R.layout.content_main);
            break;
    }
}
}
