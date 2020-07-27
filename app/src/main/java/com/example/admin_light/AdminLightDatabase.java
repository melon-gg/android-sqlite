package com.example.admin_light;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;
public class AdminLightDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Admin_Light.db";  //数据库名字
    private static final int DATABASE_VERSION = 1 ;
    static AdminLightDatabase db;
    public AdminLightDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Admin_LightTable(Light_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " datetime INTEGER)");
        for(int i = 0; i < 3; i++)
            sqLiteDatabase.execSQL("INSERT INTO Admin_LightTable(Light_ID,datetime) VALUES(" + i + "," + 10 + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
        super.onOpen(sqLiteDatabase);
    }
}
