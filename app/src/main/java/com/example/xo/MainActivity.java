package com.example.xo;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    ImageView currentPressed;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    TextView txGameMode;
    Button buTry;
    static public String gameMode="";
    TextView tvWinner;
    int TagOfPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariable();
        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.w("size",Integer.toString(width));
        gridLayout.getLayoutParams().height= width ;
        gridLayout.getLayoutParams().width = width;
        if(currentPlayer==1){
            txGameMode.setText("Hard Mode");
            hasPressed(imageView4);
        }else {
            txGameMode.setText("Easy Mode");
        }
    }
    boolean findPosition;
    boolean GameHasEnd= false ;
    boolean ThereIsAWinner = false ;
    int currentPlayer = -1 ;
    int[] PositionStatus = {2,2,2,2,2,2,2,2,2};
    int [][] WiningPosition = {{0,1,2},{3,4,5},{6,7,8},{0,4,8},{2,4,6},{0,3,6},{1,4,7},{2,5,8}};
    int [][] goodChance = {{0,4,6},{0,4,2},{2,4,8},{6,4,8}};
    int [] firstToPlay ={0,2,6,8};
    public void robotTurn(View view){
        getLocationTag();
        if (PositionStatus[TagOfPosition] == 2 && GameHasEnd == false) {
            if(currentPlayer == 1)
            {
                PositionStatus[TagOfPosition] = currentPlayer;
                findRightTag(TagOfPosition);
            }
            checkTheResult(view);
        }
        checkEndGame(view);
    }
    public void hasPressed(final View view) {
        //red -- robot = 1 | yellow -- human = 0 ;
        currentPressed = (ImageView) view;
        getLocationTag();
        if (PositionStatus[TagOfPosition] == 2 && GameHasEnd == false) {
            //human
            if(currentPlayer == 0){
                //get tag of image or position
                PositionStatus[TagOfPosition] = currentPlayer;
                findRightTag(TagOfPosition);
            }
            checkTheResult(view);
        }
        checkEndGame(view);
        if (GameHasEnd==false){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    robotTurn(view);
                }
            }, 1000);
        }
    }
    public void getLocationTag(){
        if(currentPlayer == 0){
            TagOfPosition = Integer.parseInt(currentPressed.getTag().toString());
        }
        //robot
        else if(currentPlayer == 1)
        {
            findPosition =false;
            positionTOWin();
            if(!findPosition) {
                Log.w("positionToDefeat","called");
                positionToDefeat();
            }
            if(!findPosition) {
                Log.w("PutStrategyToWin","called");
                PutStrategyToWin();
            }if(!findPosition) {
            Log.w("frist move win","called");
                firstMoveToWin();
            }
            if(!findPosition) {
                if(PositionStatus[4]==2) {
                    TagOfPosition = 4;
                    Log.w("middel","called");
                }
                else {
                    TagOfPosition = randomNumber();
                    Log.w("11", "called");
                }}}
    }
    public void initializeVariable(){
        tvWinner = (TextView) findViewById(R.id.textView);
        buTry = (Button) findViewById(R.id.butry);
        imageView1 =findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 =findViewById(R.id.imageView3);
        imageView4 =findViewById(R.id.imageView4);
        imageView5 =findViewById(R.id.imageView5);
        imageView6 =findViewById(R.id.imageView6);
        imageView7 =findViewById(R.id.imageView7);
        imageView8 =findViewById(R.id.imageView8);
        imageView9 =findViewById(R.id.imageView9);
        txGameMode =findViewById(R.id.txGameMode);
        if(gameMode=="easy"){
            currentPlayer=0;
        }else if(gameMode=="hard"){
            currentPlayer=1;
        }
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
        if(gameMode=="easy"){
            currentPlayer=0;
        }else if(gameMode=="hard"){
            currentPlayer=1;
        }
         for (int i =0 ;i<PositionStatus.length;i++)
         {
             PositionStatus[i] = 2;
         }

        if(currentPlayer==1){
            txGameMode.setText("Hard Mode");
            hasPressed(imageView4);
        }else {
            txGameMode.setText("Easy Mode");
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
                    winner = "you";
                } else if (currentPlayer == 0) {
                    winner = "robot";
                }
                GameHasEnd = true;
                ThereIsAWinner =true ;
                buTry.setVisibility(view.VISIBLE);
                tvWinner.setVisibility(view.VISIBLE);
                tvWinner.setText(winner + " Won");
            }
        }
    }
    public void animation(ImageView image){
        image.setTranslationY(-1500);
        image.animate().translationYBy(1500).rotation(3600).setDuration(300);
    }
    public int  randomNumber(){
        int max =8;
        int min =0;
        int num =0;
        num = (int)Math.floor(Math.random()*(max-min+1)+min);
        while (PositionStatus[num] != 2){
            num = (int)Math.floor(Math.random()*(max-min+1)+min);
        }
        return num;
    }
    public void findRightTag(int TagOfPosition){
           switch (TagOfPosition){
               case 0:
                   sendRightImage(imageView1);
                   break;
               case 1:
                   sendRightImage(imageView2);
                   break;
               case 2:
                   sendRightImage(imageView3);
                   break;
               case 3:
                   sendRightImage(imageView4);
                   break;
               case 4:
                   sendRightImage(imageView5);
                   break;
               case 5:
                   sendRightImage(imageView6);
                   break;
               case 6:
                   sendRightImage(imageView7);
                   break;
               case 7:
                   sendRightImage(imageView8);
                   break;
               case 8:
                   sendRightImage(imageView9);
                   break;
           }
    }
    public void sendRightImage(ImageView image){
        animation(image);
        setImageAndChangePlayer(image);
    }
    public void setImageAndChangePlayer(ImageView image){
        if (currentPlayer == 0) {
            image.setImageResource(R.drawable.yellow);
            currentPlayer = 1;
        } else if (currentPlayer == 1) {
            image.setImageResource(R.drawable.red);
            currentPlayer = 0;
        }
    }
    public void positionTOWin(){
        for(int[] win:WiningPosition){
            if(PositionStatus[win[0]] == 1 && PositionStatus[win[1]] == 1 && PositionStatus[win[2]] == 2){
                TagOfPosition = win[2];
                 findPosition = true;
                Log.w("0","called");
                break;
            }else if(PositionStatus[win[1]]== 1 && PositionStatus[win[2]] == 1 && PositionStatus[win[0]] == 2){
                TagOfPosition = win[0];
                findPosition =true;
                Log.w("1","called");
                break;
            }
            else if(PositionStatus[win[0]]== 1 && PositionStatus[win[2]]== 1 && PositionStatus[win[1]]==2){
                TagOfPosition = win[1];
                findPosition =true;
                Log.w("2","called");
                break;
            }
        }

    }
    public void positionToDefeat(){
        for(int[] win:WiningPosition){
            if(PositionStatus[win[0]] == 0 && PositionStatus[win[1]] == 0 && PositionStatus[win[2]] == 2){
                TagOfPosition = win[2];
                findPosition = true;
                Log.w("3","called");
                break;
            }
            else if(PositionStatus[win[1]]== 0 && PositionStatus[win[2]] == 0 && PositionStatus[win[0]] == 2){
                TagOfPosition = win[0];
                findPosition = true;
                Log.w("4","called");
                break;
            }
            else if(PositionStatus[win[0]]== 0 && PositionStatus[win[2]]==0 && PositionStatus[win[1]]==2){
                TagOfPosition = win[1];
                findPosition = true;
                Log.w("5","called");
                break;
            }
        }
    }
    public void PutStrategyToWin(){
        for(int[] haveAChance:goodChance){
            if(PositionStatus[haveAChance[0]] == 1 && PositionStatus[haveAChance[1]] == 1 && PositionStatus[haveAChance[2]] == 2){
                TagOfPosition = haveAChance[2];
                findPosition = true;
                Log.w("6","called");
                break;
            }else if(PositionStatus[haveAChance[1]]== 1 && PositionStatus[haveAChance[2]] == 1 && PositionStatus[haveAChance[0]] == 2){
                TagOfPosition = haveAChance[0];
                findPosition =true;
                Log.w("7","called");
                break;
            }
            else if(PositionStatus[haveAChance[0]]== 1 && PositionStatus[haveAChance[2]]== 1 && PositionStatus[haveAChance[1]]==2) {
                TagOfPosition = haveAChance[1];
                findPosition = true;
                Log.w("8", "called");
                break;
            }
        }
    }
    public void firstMoveToWin(){
        Log.w("frist move win","called");
        for(int[] haveAChance:goodChance){
            if(PositionStatus[haveAChance[1]] == 1 && PositionStatus[haveAChance[0]] == 2){
                TagOfPosition = haveAChance[0];
                findPosition = true;
                Log.w("9","called");
                break;
            }else if(PositionStatus[haveAChance[1]] == 1 && PositionStatus[haveAChance[2]] == 2){
                TagOfPosition = haveAChance[2];
                findPosition = true;
                Log.w("10","called");
                break;
            }
         }
    }
}