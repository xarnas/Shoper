package com.arnas.shoper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaspetrauskas on 23/03/2017.
 */

public class CategoryList {
    public int id;
    public String name;

    CategoryList(int id,String cat){
        setId(id);
        setName(cat);

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
