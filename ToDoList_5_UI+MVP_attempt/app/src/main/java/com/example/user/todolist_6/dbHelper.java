package com.example.user.todolist_6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class dbHelper extends SQLiteOpenHelper{



    private static final String DB_NAME = "ToDoApp4";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Task";
    private static final String DB_COLUMN_ID = "ID";
    private static final String DB_COLUMN_TITLE = "TaskTitle";
    private static final String DB_COLUMN_DETAILS = "TaskDetails";

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        try{
//            String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);", DB_TABLE, DB_COLUMN_TITLE, DB_COLUMN_DETAILS);
//            db.execSQL(query);
//        } catch (SQLException e) {
//            Log.d(TAG, " Error create database " + e.getMessage());
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_COLUMN_TITLE + " TEXT, "+ DB_COLUMN_DETAILS + "TEXT);";
                 db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DROP TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
        }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();

    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN_TITLE + " = ?", new String[]{task});
        db.close();
    }

    public void insertNewTask(String title, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_TITLE, title);
        values.put(DB_COLUMN_DETAILS, details);
        try {
            db.insertOrThrow(DB_TABLE, null, values);
            Log.d(TAG, "Added");

        } catch (SQLException e) {
            Log.d(TAG, "Error " + e.getCause() + " " + e.getMessage());
        }
        db.close();
    }





    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN_TITLE}, null, null, null, null, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN_TITLE);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;

    }


//    public String getTaskDetails(String title){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " +
//                DB_COLUMN_TITLE + " = '" + title + "'", null);
//        String details;
//        if (cursor.moveToFirst()){
//            details = ;
//        }else {
//            details = null;
//        }
//
//        cursor.close();
//        return details;
//    }
}
