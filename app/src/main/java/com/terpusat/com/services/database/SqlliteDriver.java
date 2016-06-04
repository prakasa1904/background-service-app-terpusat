package com.terpusat.com.services.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prakasa on 04/06/16.
 */
public class SqlliteDriver extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TerpusatService.db";
    public static final String PENGATURAN_TABLE_NAME = "service_pengaturan";
    public static final String PENGATURAN_COLUMN_ID = "id";
    public static final String PENGATURAN_COLUMN_NAMA = "nama";
    public static final String PENGATURAN_COLUMN_TIME = "time";
    public static final String PENGATURAN_COLUMN_URL = "url";
    public static final String PENGATURAN_COLUMN_STATUS = "status";
    private HashMap hp;

    public SqlliteDriver(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table service_pengaturan " +
                        "(id integer primary key, nama text, waktu text,url text, integer status)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS service_pengaturan");
        onCreate(db);
    }

    public boolean insertConfig(String nama, String time, String url, Integer status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("time", time);
        contentValues.put("url", url);
        contentValues.put("status", status);
        db.insert("config", null, contentValues);
        return true;
    }

    public boolean updateConfig(Integer id, String time, String url, Integer status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("url", url);
        contentValues.put("status", status);
        db.update("service_pengaturan", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteConfig(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("service_pengaturan",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PENGATURAN_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from service_pengaturan", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PENGATURAN_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getDataById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from service_pengaturan where id="+id+"", null );
        return res;
    }
}
