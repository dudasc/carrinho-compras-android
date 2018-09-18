package com.esc.compras.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.esc.compras.model.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemDAO {

    private static final String TAG = "ItemDAO";
    private final Context context;

    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    private final int version = 1;

    public ItemDAO(Context context) {
        this.context = context;

        String[] sqlOnCreate = new String[]{
            "create table item (_id integer primary key autoincrement, name text not null);"
        };

        String sqlOnUpdate = null;

        this.dbHelper = new SQLiteHelper(context, "items", version, sqlOnCreate, sqlOnUpdate);
        this.db = dbHelper.getWritableDatabase();
    }

    public void saveItem(Item item) {
        db.insert("item", null, item.toContentValues());
    }

    public void updateItem(Item item) {
        db.update("item", item.toContentValues(), "_id = ?", new String[]{"" + item.getId()});
    }

    public void deleteItem(Item item) {
        db.delete("item", "_id = ?", new String[]{"" + item.getId()});
    }

    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();

        Cursor c = db.query("item", new String[]{"_id", "name"}, null, null, null, null, null);
        if(c.getCount() > 0){
            c.moveToFirst();

            do {
                Item item = new Item();
                item.setId(c.getLong(0));
                item.setName(c.getString(1));

                items.add(item);
            } while(c.moveToNext());
        }

        return items;
    }

    public List<Item> findByName(String name) {
        List<Item> items = new ArrayList<>();

        Cursor c = db.query("item", new String[]{"_id", "name"}, "name LIKE ?", new String[]{name + "%"}, null, null, null);
        if(c.getCount() > 0){
            c.moveToFirst();

            do {
                Item item = new Item();
                item.setId(c.getLong(0));
                item.setName(c.getString(1));

                items.add(item);
            } while(c.moveToNext());
        }

        return items;
    }

    public void close() {
        this.dbHelper.close();
    }

}
