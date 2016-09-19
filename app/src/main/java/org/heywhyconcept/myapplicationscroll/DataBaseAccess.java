package org.heywhyconcept.myapplicationscroll;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private static SQLiteDatabase database;
    private static DataBaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DataBaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DataBaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            //this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public ArrayList getQuotes1() {
        ArrayList<String> list1 = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT prayer FROM August", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list1.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list1;

    }

    public ArrayList getQuotes2() {
        ArrayList<String> list2 = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT prayer FROM September", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list2.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list2;
    }
    public ArrayList getQuotes() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT prayer FROM July", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        list.addAll(getQuotes1());
        list.addAll(getQuotes2());
        return list;
    }
    public ArrayList getTitles() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Title", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;

    }
    public ArrayList getDate() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Date", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}

