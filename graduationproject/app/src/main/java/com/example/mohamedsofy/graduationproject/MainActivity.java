package com.example.mohamedsofy.graduationproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.bumptech.glide.Glide;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    // take object from Database class
    Database db = new Database(this);


    //Button & imageview
    Button take_image_btn, speaker_btn, login_btn;
    ImageView imageView;


    //voice & Camera
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    private DrawerLayout mdrawerLayout;  // navigation
    private ActionBarDrawerToggle mtoggle;
    private static final int PICK_IMAGE = 100;
    Uri imageuri;
    Bitmap bitmap;
    Bitmap photo;
    int Rrquest_camera = 0;
    Intent intent;
    public static final int RequestPermissionCode = 1;
    TextToSpeech speach;

    // Variables
    boolean result;
    String name;
    public String ImageData;


    // Upload URL
    //  https://hotelex.000webhostapp.com/uploadImage.php
    // 10.23.1.13:3232uploadImage
    private String uploadurl = "http://192.168.43.97:3232/uploadImage";
    String Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        take_image_btn = (Button) findViewById(R.id.take_photo);
        speaker_btn = (Button) findViewById(R.id.speak);
        login_btn = (Button) findViewById(R.id.login);
        imageView = (ImageView) findViewById(R.id.image_view);


        // check if user make login or not
        check_login();


        // check if mobile device have camera or not
        if (!hasCamera()) {
            take_image_btn.setEnabled(false);
        }
        EnableRuntimePermission();


        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.open, R.string.close);
        mdrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_vi);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);

                switch (item.getItemId()) {
                    case R.id.add_person: {
                        Intent intent = new Intent(MainActivity.this, Add_person.class);
                        startActivity(intent);

                        break;
                    }
                    case R.id.Settings: {
                        Intent intent = new Intent(MainActivity.this, settings.class);
                        startActivity(intent);

                        break;
                    }
                    case R.id.About: {
                        Intent in = new Intent(MainActivity.this, About.class);
                        startActivity(in);

                        break;
                    }
                    case R.id.logout: {
                        Intent in = new Intent(MainActivity.this, Logout.class);
                        startActivity(in);

                        break;
                    }

                }

                mdrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    // menu sellection item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void check_login() {
        result = db.check_status("login");
        if (result == false) {
            //  login_btn.setVisibility(View.VISIBLE);
            take_image_btn.setVisibility(View.INVISIBLE);
            speaker_btn.setVisibility(View.INVISIBLE);
            speach_Money_and_Face("Make Login Please");
            // Toast.makeText(this, " Make Login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);

        } else {
            login_btn.setVisibility(View.INVISIBLE);
            take_image_btn.setVisibility(View.VISIBLE);
            speaker_btn.setVisibility(View.VISIBLE);
        }
    }


    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

    }

    public void open_camera(View view) {

        intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);


    }



      public void waiting(final String result){

          final ProgressDialog dialog = ProgressDialog.show(this, "", "waiting processing ....",
                  true);


          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
              public void run() {
                  dialog.dismiss();
                  if (result.equals("article")) {
                      // Toast.makeText(this, " in hand", Toast.LENGTH_SHORT).show();
                      String text = "problems there some problems in the " +
                              "much easier than the current" +
                              "some of thes problems " +
                              "the current operations " +
                              "time effort and cost";
                      speach_Money_and_Face(text);
                      Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                  }
                  else if (result.equals("money")) {

                      String money = "20 pounds";
                      speach_Money_and_Face(money);
                      Toast.makeText(getApplicationContext(), money, Toast.LENGTH_LONG).show();
                  }
                  else if (result.equals("face")) {
                      String face = "Mohamed Sofy";
                      Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                      speach_Money_and_Face(face);

                  }
                  else{
                      String error_mes= "Your voice is not recognized";
                      Toast.makeText(getApplicationContext(), error_mes, Toast.LENGTH_LONG).show();
                      speach_Money_and_Face(error_mes);
                  }


              }
          }, 3000);

      }





    @Override
    protected void onActivityResult(int requestCode, int resultCodee, Intent data) {
        if (requestCode == 1 && resultCodee == RESULT_OK ) {

            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);

          //  photo = (Bitmap) data.getExtras().get("data");

           // ByteArrayOutputStream stream = new ByteArrayOutputStream();
          //  photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
         //   byte[] byteArray = stream.toByteArray();
          //  Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

          //  imageView.setImageBitmap(bitmap);

        } else if (requestCode == REQ_CODE_SPEECH_INPUT) {

            if (resultCodee == RESULT_OK && data != null) {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //mVoiceInputTv.setText(result.get(0));
                if (result.get(0).equals("article")) {
                 //   waiting("article");
                    // Toast.makeText(this, " in hand", Toast.LENGTH_SHORT).show();
                  /* String text = "problems there some problems in the " +
                            "much easier than the current" +
                            "some of thes problems " +
                            "the current operations " +
                            "time effort and cost";
                    speach_Money_and_Face(text);
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                */

                    StringBuilder text_result = ocr_processing();
                    Toast.makeText(getApplicationContext(), text_result, Toast.LENGTH_LONG).show();
                    speach_Article(text_result);

                }
                else if (result.get(0).equals("money")) {
                  //  waiting("money");
                    /*
                   String money = "20 pounds";
                    speach_Money_and_Face(money);
                    Toast.makeText(getApplicationContext(), money, Toast.LENGTH_LONG).show();
                    */
                    /////////////////////
                     new_uploadImage("money");
                  //  Toast.makeText(getApplicationContext(), money_result, Toast.LENGTH_LONG).show();
                  //  speach_Money_and_Face(money_result);
                }
                else if (result.get(0).equals("face")) {
                 //   waiting("face");
                    /*
                    String face = "Mohamed Sofy";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    speach_Money_and_Face(face);
                    */
                    /////////////////////
                     new_uploadImage("face");
                    //  Toast.makeText(getApplicationContext(), money_result, Toast.LENGTH_LONG).show();
                   //   speach_Money_and_Face(money_result);
                }
                else{
                    String error_mes= "Your voice is not recognized";
                    Toast.makeText(getApplicationContext(), error_mes, Toast.LENGTH_LONG).show();
                    speach_Money_and_Face(error_mes);
                }
            }
        }
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(MainActivity.this, "CAMERA permission " +
                    "allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }


    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    //   Toast.makeText(MainActivity.this,
                    // "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(MainActivity.this, "Permission Canceled, " +
                            "Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // Speaker Functions

    @Override
    protected void onPause() {
        if (speach != null) {
            speach.stop();
            speach.shutdown();
        }
        super.onPause();
    }

    private void speach_Article(final StringBuilder text) {
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

    private void speach_Money_and_Face(final String text) {
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }


    // headphone button function

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            if(photo != null){
                startVoiceInput();
            }else{
                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            }


            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    // image to text alg.orithm (osr)
    private StringBuilder ocr_processing() {
        StringBuilder stringBuilder = null;
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        } else {

            Frame frame = new Frame.Builder().setBitmap(photo).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); ++i) {
                TextBlock item = items.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");

            }
        }
        return stringBuilder;
    }


    public void speaker_btn(View view) {

      //  StringBuilder text_result = ocr_processing();
      //  Toast.makeText(getApplicationContext(), text_result, Toast.LENGTH_LONG).show();
      //  speach(text_result);
        startVoiceInput();

      //  new_uploadImage();


    }


    /////////////  upload image


    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
        byte[] imageByte = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return encodedImage;
    }

    private void new_uploadImage(final String Alg_name){


        // https://sync-application.herokuapp.com/register

        //https://herokuapitest.herokuapp.com/
        //https://192.168.43.97:3232
        final String url = "http://192.168.43.104:3232"; // your URL
        //Showing the progress dialog
       // final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                      //  loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(MainActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                      //  loading.dismiss();

                        //Showing toast
                        Toast.makeText(MainActivity.this, ""+volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = imageToString(photo);
                Map<String,String> params = new Hashtable<String, String>();
                params.put("name", Alg_name);
                params.put("image", image);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    // Function to send image to webservice
    private void upload_image() {


        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.5:3030"; // your URL
        //speaker_btn.setEnabled(false);


        HashMap<String, String> params = new HashMap<String,String>();


        params.put("name", "face");
     //  Toast.makeText(getApplicationContext(),ImageData,
        //        Toast.LENGTH_LONG).show();
        Log.e("fdg","e1");
        ImageData = imageToString(photo);
        Log.e("fdg","e2");
        params.put("image", ImageData);
        Log.e("fdg","e3");

        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("name"),
                                    Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString()+"fgfg",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });



            jsObjRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });

        queue.add(jsObjRequest);
    }





    ////////////////////////////
    private void test() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(getApplicationContext(),Response,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                  String ImageData = imageToString(photo);
                params.put("name", "face");
                params.put("image", ImageData);

                //  params.put("name", "mm");
                return params;
            }
        };
        Mysignal.getInstance(MainActivity.this).addToRequestQue(stringRequest);

    }



}