package com.esc.compras.model;

import android.content.ContentValues;

import java.io.Serializable;

public class Item implements Serializable {

    private Long id;
    private String name;

    public Item(){}

    public Item(String name) {
        this.name = name;
    }

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("name", this.name);

        if(id != null) {
            values.put("id", this.id);
        }

        return values;
    }

}
