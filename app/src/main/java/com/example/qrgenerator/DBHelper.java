package com.example.qrgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 3;
        private static final String DATABASE_NAME ="student.db";
        static final String TABLE_NAME = "students";
        static final String ID_COL = "_id";
        static final String NAME_COL = "username";
        static final String STUDENT_ID_COL = "student_id"; //lower is better

        public DBHelper(Context context){
            super(context,DATABASE_NAME, null, DATABASE_VERSION );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String create_table = "create table " + TABLE_NAME
                    + "("
                    + ID_COL + " integer primary key autoincrement,"
                    + NAME_COL + " text not null,"
                    + STUDENT_ID_COL + " text not null"
                    + ")";
            db.execSQL(create_table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " +  TABLE_NAME);
            onCreate(db);
        }

        public boolean insertStudent(String name, int s_id){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_COL, name);
            values.put(STUDENT_ID_COL, s_id);
            db.insert(TABLE_NAME, null, values);
            return true;
        }

    /*public Cursor getPlayer(String usr){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStr = "select * from " + TABLE_NAME + " where "
                + NAME_COL + " = " + "'" + name + "'";
        Cursor cursor = db.rawQuery(sqlStr, null);
        return cursor;
    }*/

        public Cursor getAllStudents(){
            SQLiteDatabase db = this.getReadableDatabase();
            String sqlStr = "select * from " + TABLE_NAME
                    + " order by "
                    + STUDENT_ID_COL+ " ASC, "
                    + NAME_COL + " ASC";
            return db.rawQuery(sqlStr, null);
        }
    }

