package com.example.manep.loginactivitycabelas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static EditText username;
    private static EditText password;
    private static Button login,register,delete;
    private static TextView attempts;
    public static CheckBox checkremember;
    DataBaseHelper DOP = new DataBaseHelper(this);
    private static final String myprefence = "Myprefence";
    private static final String userpref = "userkey";
    private static final String passwordpref = "passwordkey";

    int attempt_count = 3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_edit);
        password =findViewById(R.id.password_edit);
        checkremember = findViewById(R.id.chckRemeber);
        sharedPreferences = getSharedPreferences(myprefence, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userpref)){
            username.setText(sharedPreferences.getString(userpref,""));
        }
        if (sharedPreferences.contains(passwordpref)){
            password.setText(sharedPreferences.getString(passwordpref,""));
        }
        checkremember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(userpref, username.getText().toString());
                editor.putString(passwordpref, password.getText().toString());
                editor.commit();
            }
        });
        register = findViewById(R.id.BtnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =username.getText().toString();
                String pass =password.getText().toString();
                DOP.putInformation(name,pass);
                username.setText("");
                password.setText("");
            }
        });
        login = findViewById(R.id.BtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean loginstatus = false;
                final Cursor cr =DOP.getInfromation();
                cr.moveToNext();
                do {
                    if (username.getText().toString().equals(cr.getString(0)) &&
                            password.getText().toString().equals(cr.getString(1))) {
                        loginstatus = true;
                    }
                }while (cr.moveToNext()) ;
                if (loginstatus == true)
                {Toast.makeText(MainActivity.this, "Credentials are correct", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    if (checkremember.isChecked())
                    {checkremember.setEnabled(true);}}
                else {
                    Toast.makeText(MainActivity.this, "Credentials are incorrect", Toast.LENGTH_SHORT).show();
                    attempt_count--;
                    attempts.setText(Integer.toString(attempt_count));
                    username.setText("");
                    password.setText("");

                    if (attempt_count == 0) {
                        //attempt_count=5;
                        login.setEnabled(false);
                    }
                }

            }
        });
        delete = findViewById(R.id.BtnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =username.getText().toString();
                String pass =password.getText().toString();
                boolean delstatus = false;
                final Cursor cr =DOP.getInfromation();
                cr.moveToNext();
                do {
                    if (username.getText().toString().equals(cr.getString(0))||
                            password.getText().toString().equals(cr.getString(1))){
                        delstatus =true;}
                }while (cr.moveToNext());

                if (delstatus == true) {
                    DOP.delInformation(name, pass);
                }
                else {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
