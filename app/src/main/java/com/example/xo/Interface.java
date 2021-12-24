package com.example.xo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Interface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
    }

    public void onPressed(View view) {
        Intent goToNextActivity;
        switch (view.getId()){
            case R.id.BuRobot:
                 goToNextActivity = new Intent(getApplicationContext(), level.class);
                 startActivity(goToNextActivity);
                break;
            case R.id.buHumman:
                 goToNextActivity = new Intent(getApplicationContext(), PlayWithHuman.class);
                 startActivity(goToNextActivity);
                break;
        }

    }
}