package com.hch.hooney.filecontrolproject.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hch.hooney.filecontrolproject.Do.MainData;

import java.util.ArrayList;

public class DatabaseHandler {
    private static final String db_name = "main_database.sqlite";
    public static DatabaseHandler instance;
    public DatabaseOpenHelper helper;

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler();
            instance.helper = new DatabaseOpenHelper(context, db_name, null, 1);
        }
        return instance;
    }

    public static void insertAll(ArrayList<MainData> list){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        for(MainData item : list){
            ContentValues values = new ContentValues();
            values.put("i_name", item.getM_name());
            values.put("i_post", Integer.parseInt(item.getM_postNumber()));
            values.put("i_adr1", item.getM_address1());
            values.put("i_adr2", item.getM_address2());
            db.insert("main_data", null, values);
        }
    }

    public static void deleteAll(){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        db.delete("main_data", null, null);
    }

    public static ArrayList<MainData> selectAll(){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        Cursor cursor = db.query("main_data", null, null, null, null, null, null);
        ArrayList<MainData> temp = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                MainData item = new MainData();
                item.setM_name(cursor.getString(1));
                item.setM_postNumber(""+cursor.getInt(2));
                item.setM_address1(cursor.getString(3));
                item.setM_address2(cursor.getString(4));
                temp.add(item);
            }while (cursor.moveToNext());
        }
        return temp;
    }
}
