package com.example.mohamedsofy.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener  {

    Switch settingS_switch;
    EditText ip_cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingS_switch =(Switch)findViewById(R.id.switch1);
        ip_cam =(EditText)findViewById(R.id.ip_cam);
      //  ip_cam.setEnabled(false);


        settingS_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(settingS_switch.isChecked()){
            ip_cam.setEnabled(true);
            Toast.makeText(this,"Enter Camera IP",Toast.LENGTH_LONG).show();
        }else{
            ip_cam.setEnabled(false);
        }
    }
}
