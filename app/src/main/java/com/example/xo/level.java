package com.example.xo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void onPressed(View view) {
        Intent goToNextActivity;
        switch (view.getId()){
        case R.id.BuEasy:
        MainActivity.gameMode = "easy";
            goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToNextActivity);
            break;
        case R.id.BuHard:
            MainActivity.gameMode = "hard";
            goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToNextActivity);
            break;
    }
    }
}