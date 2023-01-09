package com.example.qrgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    EditText nameText;
    EditText idText;
    Button generateButton;
    ImageView qrImage;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.nameText);
        idText = findViewById(R.id.idText);
        generateButton = findViewById(R.id.generateButton);
        qrImage = findViewById(R.id.qrImage);
        dbHelper = new DBHelper(this);
        //test_db();
    }
    public void generateImage(View view){
        String nText = nameText.getText().toString().trim();
        String iText = idText.getText().toString().trim();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(iText, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrImage.setImageBitmap(bitmap);
            InputMethodManager manager = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE
            );
            manager.hideSoftInputFromWindow(idText.getApplicationWindowToken(),0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        dbHelper.insertStudent(nText, Integer.parseInt(iText.toString()));


    }
    private void test_db(){
        Cursor cursor=dbHelper.getAllStudents();
        if(cursor!=null){
            cursor.moveToFirst();
            do{
                String username = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL));
                String student_id = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_ID_COL));
                Log.i("player",username+" "+student_id);
            }while(cursor.moveToNext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu:
                Intent i = new Intent(this,ListActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}