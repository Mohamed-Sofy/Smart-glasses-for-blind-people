package com.example.mohamedsofy.graduationproject;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


public class Registration extends AppCompatActivity {

    EditText name, email, pass;
    Database db = new Database(this);
    TextToSpeech speach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText) findViewById(R.id.e_regist_name);
        email = (EditText) findViewById(R.id.e_regist_email);
        pass = (EditText) findViewById(R.id.e_regist_pass);
    }

    public void make_registration(View view) {

        if (name.getText().toString().equals("") && email.getText().toString().equals("") &&
                pass.getText().toString().equals("")) {
            speach_Text("Information Not Vaild ");
            //Toast.makeText(this, "Information Not Vaild ", Toast.LENGTH_SHORT).show();
        } else {
            boolean result = db.insert_status("0", name.getText().toString(), email.getText().toString(),
                    pass.getText().toString(), "true", "login");
            if (result == true) {
                speach_Text("Success Register ");
               // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
            } else {
                speach_Text("Not Vaild Information ");
               // Toast.makeText(this, "Not Vaild Information", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void speach_Text(final String text) {
        speach = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int st) {
                if (st != TextToSpeech.ERROR) {
                    speach.setLanguage(Locale.UK);
                    speach.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

}