package com.sadman.app.list_test;

//import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 11/1/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context contex){
        super(contex, "sadDB", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("[sadDB]", "--- onCreate db ---" );
//        db.execSQL(
//                "create table position("
//                        +"id integer primary key autoincrement,"
//                        +"name text"
//                        +");"
//        );
        db.execSQL(
                "create table worker("
                        + " id integer primary key autoincrement, "
                        + " name text, "
//                        +"position_id integer"
                        + " position text, "
                        + " salary integer "
//                        +"foreign key(position_id) references position(id)"
                        +");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
