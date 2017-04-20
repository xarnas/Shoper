package com.arnas.shoper;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 04/04/2017.
 */

public class DyMealsList3 extends AppCompatActivity {

        protected String Name;
        protected int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    DyMealsList3(int id,String Name){
            setName(Name);
            setId(id);
        }

        public String getName() {
            return Name;
        }
        public void setName(String name) {
            Name = name;
        }





    }
