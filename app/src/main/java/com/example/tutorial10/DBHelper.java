package com.example.tutorial10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_name = "UserDB";
    public static final String TBL_Name = "user";
    public static final int DB_Version = 1;

    public DBHelper(Context context) {
        super(context, DB_name, null, DB_Version);
    }

    public static final String COLUMN_ID = "u_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_UNAME = "uname";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASS = "pass";

    public String CREATE_USER_TABLE = "CREATE TABLE if not exists " + TBL_Name + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_UNAME + " TEXT," + COLUMN_PHONE + " TEXT," + COLUMN_PASS + " TEXT" + ")";
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + TBL_Name);
    }

    public boolean insert(String name, String uname, String phone, String pass) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, String.valueOf(name));
        values.put(COLUMN_UNAME, String.valueOf(uname));
        values.put(COLUMN_PHONE, String.valueOf(phone));
        values.put(COLUMN_PASS, String.valueOf(pass));

        long result = MyDB.insert(TBL_Name,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean checkusernamepassword(String uname,String pass){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where uname = ? and pass = ?",new String[]{uname,pass});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
