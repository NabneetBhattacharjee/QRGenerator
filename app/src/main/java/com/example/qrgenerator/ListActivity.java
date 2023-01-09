package com.example.qrgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private static final int NAME_LENGTH = 10;
    private static final int STUDENT_ID_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHelper = new DBHelper(this);
        TextView headerRow = findViewById(R.id.headerRow);
        String tempStr = fixedLengthString("Name",NAME_LENGTH)
                + fixedLengthString("Student ID",STUDENT_ID_LENGTH);
        headerRow.setText(tempStr);
        ArrayAdapter<String> scoreAdapter = new ArrayAdapter<>(this,R.layout.item);
        ListView playerList = findViewById(R.id.studentList);
        playerList.setAdapter(scoreAdapter);

        Cursor cursor = dbHelper.getAllStudents();
        if(cursor!=null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    String nameStr = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL));
                    nameStr = nameStr.trim();
                    nameStr = fixedLengthString(nameStr,NAME_LENGTH);
                    String scoreStr = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_ID_COL));
                    scoreStr = scoreStr.trim();
                    scoreStr = fixedLengthString(scoreStr,STUDENT_ID_LENGTH);

                    scoreAdapter.add(nameStr + scoreStr );
                }while(cursor.moveToNext());
            }
        }
    }

    private String fixedLengthString(String string, int length) {
        return String.format("%1$-" + length + "s", string);
    }

}