package com.example.manep.loginactivitycabelas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by manep on 2/26/2018.
 */

public class Home extends AppCompatActivity {
    Button database;
    DataBaseHelper DOP = new DataBaseHelper(this);
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);
        database = findViewById(R.id.BtnDataBase);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = DOP.getAll();
                if (c.getCount() == 0) {
                    showMessage("Error", "No wish list");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while ((c.moveToNext())) {
                    stringBuffer.append("USER_NAME :" + c.getString(0) + "\n");
                    stringBuffer.append("PASSWORD:" + c.getString(1) + "\n\n");

                }
                showMessage("Student Details", stringBuffer.toString());

            }
        });
    }
    private void showMessage(String title,String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle(title);
        builder.setMessage(response);
        builder.show();

    }
}
