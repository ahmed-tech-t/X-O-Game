package com.example.xo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayWithHuman extends AppCompatActivity {
    ImageView currentPressed;
    Button buTry;
    TextView tvWinner;
    int TagOfPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.grid);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.w("size",Integer.toString(width));
        gridLayout.getLayoutParams().height= width ;
        gridLayout.getLayoutParams().width = width;
    }

    boolean GameHasEnd = false;
    boolean ThereIsAWinner = false;
    int currentPlayer = 0;
    int[] PositionStatus = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] WiningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};

    public void hasPressed(View view) {
        initializeVariable(view);
        TagOfPosition = Integer.parseInt(currentPressed.getTag().toString());
        if (PositionStatus[TagOfPosition] == 2 && GameHasEnd == false) {
            animation();
            PositionStatus[TagOfPosition] = currentPlayer;
            setImageAndChangePlayer();
            checkTheResult(view);
        }
        checkEndGame(view);
    }

    public void initializeVariable(View view){
        currentPressed = (ImageView) view;
        tvWinner = (TextView) findViewById(R.id.textView);
        buTry = (Button) findViewById(R.id.butry);
    }
    public void buPlayAgain(View view) {
        buTry.setVisibility(view.INVISIBLE);
        tvWinner.setVisibility(view.INVISIBLE);

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        for (int i =0 ; i<grid.getChildCount() ;i++)
        {
            ImageView image = (ImageView) grid.getChildAt(i);
            image.setImageDrawable(null);
        }
        GameHasEnd= false ;
        ThereIsAWinner = false ;
        currentPlayer = 0 ;
        for (int i =0 ;i<PositionStatus.length;i++)
        {
            PositionStatus[i] = 2;
        }
    }
    public void checkEndGame(View view){
        int c =0 ;
        for(int i =0 ; i<PositionStatus.length;i++)
        {
            if(PositionStatus[i]!=2)
            {
                c++;
            }
        }
        if(c==9){
            GameHasEnd=true;
        }
        if(GameHasEnd==true&&ThereIsAWinner==false)
        {
            buTry.setVisibility(view.VISIBLE);
            tvWinner.setVisibility(view.VISIBLE);
            tvWinner.setText("Draw");
        }
    }
    public void checkTheResult(View view){
        for (int[] winPosition : WiningPosition) {
            if (PositionStatus[winPosition[0]] == PositionStatus[winPosition[1]] && PositionStatus[winPosition[1]] == PositionStatus[winPosition[2]] && PositionStatus[winPosition[0]] != 2) {

                String winner = "";
                if (currentPlayer == 1) {
                    winner = "Yellow";
                } else if (currentPlayer == 0) {
                    winner = "Red";
                }
                GameHasEnd = true;
                ThereIsAWinner =true ;
                buTry.setVisibility(view.VISIBLE);
                tvWinner.setVisibility(view.VISIBLE);
                tvWinner.setText(winner + " Has Won");
            }
        }
    }
    public void animation(){
        currentPressed.setTranslationY(-1500);
        currentPressed.animate().translationYBy(1500).rotation(3600).setDuration(300);
    }
    public void setImageAndChangePlayer(){
        if (currentPlayer == 0) {
            currentPressed.setImageResource(R.drawable.yellow);
            currentPlayer = 1;
        } else if (currentPlayer == 1) {
            currentPressed.setImageResource(R.drawable.red);
            currentPlayer = 0;
        }
    }
}
