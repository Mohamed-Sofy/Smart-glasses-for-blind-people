package com.example.mohamedsofy.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Add_person extends AppCompatActivity {

    ImageView imageView ;
    Intent intent;
    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        imageView = findViewById(R.id.show_person);
    }

    public void take_person_image(View view) {
        intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCodee, Intent data) {


        if (requestCode == 7 && resultCodee == RESULT_OK) {

            photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
        }
    }
}
