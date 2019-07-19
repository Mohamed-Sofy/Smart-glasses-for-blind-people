package com.example.mohamedsofy.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView about_text;

    String about = " Is a mobile app and smart glasses helps blind people to improve their daily living conditions, By identifying people who are used to them and adding new people to recognize it, Identify the obstacles that meet the blind and reduce their movement, such as chairs and tables and many other things, it is can also identify money , It is also easy to read books and files and report it by headphone , The smart glasses facilitate the blind people and speed up the process of taking pictures and videos without the need of the blind to open the mobile to take pictures.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about_text = (TextView) findViewById(R.id.text_about);
        about_text.setText(about);

    }
}
