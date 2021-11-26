package com.example.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore, playerTwoScore, playerWinning;
    private Button[] buttons = new Button[9];
    private Button resetBtn;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;


    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // cols
            {0, 4, 8}, {2, 4, 6}  // cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();


        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerWinning = (TextView) findViewById(R.id.playerWinning);
        resetBtn = (Button) findViewById(R.id.reset);

        for (int i=0; i< buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button)  findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount = 0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (activePlayer){
            ((Button)v).setText("x");
            ((Button)v).setTextColor(Color.parseColor("#A9A9A9"));
            gameState[gameStatePointer]=0;
        }else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#8FBC8F"));
            gameState[gameStatePointer]=1;
        }
        rountCount ++;

        if (checkWinner()){
            if (activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!!!", Toast.LENGTH_SHORT).show();
                playAgain();
        }else if (rountCount == 9){
                playAgain();
                Toast.makeText(this,"No Winner!",Toast.LENGTH_SHORT).show();
        }else {
            activePlayer = !activePlayer;
        }
        if (playerOneScoreCount > playerTwoScoreCount){
            playerWinning.setText("Player One Is Winning!");
        }else if (playerTwoScoreCount > playerOneScoreCount){
            playerWinning.setText("Player Two Is Winning!");
        }else{
            playerWinning.setText("");
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerWinning.setText("");
                updatePlayerScore();
            }
        });

    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningPosion : winningPositions){
            if (gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                      gameState[winningPosion[0]] !=2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i< buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

}
