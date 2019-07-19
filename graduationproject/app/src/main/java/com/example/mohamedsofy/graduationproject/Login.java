package com.example.mohamedsofy.graduationproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Login extends AppCompatActivity {


    EditText username , password;
    Database db = new Database(this);
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    String info = null;
     static int na = 1 , pa = 1;
    TextToSpeech speach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.e_name);
        password = (EditText)findViewById(R.id.e_password);

    }
    public void registrationForm(View view) {

        Intent in = new Intent(Login.this,Registration.class);
        startActivity(in);
    }

    public void make_login(View view) {

        if( username.getText().toString().equals("")  ||
                password.getText().toString().equals("")){
            speach_Text("Information Not Valid");
          //  Toast.makeText(this, "Information Not Valid", Toast.LENGTH_SHORT).show();
        }else{
            boolean result = db.check_login(username.getText().toString(), password.getText().toString()
                    , "login");
            if(result == true) {
                speach_Text("login Vaild");
              //  Toast.makeText(this, " login Vaild", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this , MainActivity.class);
                startActivity(intent);
            }else{
                speach_Text("Need to Redistration");
                //Toast.makeText(this, "Need to Redistration", Toast.LENGTH_SHORT).show();
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
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, Enter Your Information?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){

            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   // username.setText(result.get(0));
                    if(na==0){
                        username.setText(result.get(0));
                        na = 1;
                    }
                     if(pa == 0){
                        password.setText(result.get(0));
                        pa = 1;
                    }

                }
                break;
        }
    }

    public void voice_name(View view) {
        na = 0;
        startVoiceInput();

        //Toast.makeText(this, "username", Toast.LENGTH_SHORT).show();

    }

    public void voice_password(View view) {
       pa = 0;
        startVoiceInput();
    }


}
