package com.example.mohamedsofy.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Logout extends AppCompatActivity {

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
    }

    public void logout_btn(View view) {

        boolean result = db.logout_function("login");
        if(result == true){
            Toast.makeText(getApplicationContext(),"LogOut",Toast.LENGTH_LONG).show();
            finish();
            System.exit(0);
        }
        else {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }

    }

}
