package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button resetGame;
    private Button [][][] buttons = new Button[4][4][4];
     int [][][] gameState = new int[4][4][4];
    private int playCount;
    private boolean activePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetGame = (Button) findViewById(R.id.resetGame);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                for (int k = 0; k < buttons[i][j].length; k++) {
                    String buttonID = "g" + i + "r" + j + "c" + k;
                    int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    buttons[i][j][k] = (Button) findViewById(resourceID);
                    buttons[i][j][k].setOnClickListener(this);

                }
            }
        }
        activePlayer = true;
        playCount = 0;


    }



    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }

        String buttonID = v.getResources().getResourceEntryName(v.getId()); // Gets the name of the button pressed
        int grid = Integer.parseInt(buttonID.substring(1,2));
        int row = Integer.parseInt(buttonID.substring(3,4));
        int column = Integer.parseInt(buttonID.substring(5));
        ((Button) v).setTextSize(26);
        ((Button) v).setPadding(0,0,0,0);
        if(activePlayer){
            ((Button) v).setText("X");
            gameState[grid][row][column] = 1;
        } else {
            ((Button) v).setText("O");
            gameState[grid][row][column] = 2;
        }
        playCount++;
        if(hasWon(grid, row, column)) {
            if(activePlayer) {
                Toast.makeText(this, "X has won!", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(this, "O has won!", Toast.LENGTH_LONG);
            }
            playAgain();
        } else if (playCount == 64) {

        } else {
            activePlayer = !activePlayer;
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 playAgain();
             }
        });





        //Log.i("test", "button is clicked!");
    }

    public boolean hasWon (int g, int r, int c) {
        int player = (activePlayer) ? 1:2;

        //determines if there is a winner in the row on the same grid
       int rCount = 0;
       for (int i = 0; i <= 3; i++) {
           if (gameState[g][r][i] == player) rCount++;
       }
       if (rCount == 4) return true;

       //determines if there is a winner in the column on the same grid
        int cCount = 0;
        for (int i = 0; i <= 3; i++) {
            if (gameState[g][i][c] == player) cCount++;
        }
        if (cCount == 4) return true;

        //determines if there is a "stacked" winner, the same position on all four grids
        int sCount = 0;
        for (int i = 0; i <= 3; i++) {
            if (gameState[i][r][c] == player) sCount ++;
        }
        if (sCount == 4) return true;

        //determines if there is a vertical winner across grids
        int vCount = 0;
        for (int i = 0; i <= 3; i++) {
            if (gameState[i][i][c] == player) vCount ++;
        }
        if (vCount == 4) return true;

        //determines if there is a horizontal winner across grids
        int hCountNeg = 0;
        int hCountPos = 0;
        for (int i = 0; i <= 3; i++) {
            if (gameState[i][r][i] == player) hCountPos ++;
            if (gameState[i][r][3-i] == player) hCountNeg ++;
        }
        if (hCountNeg == 4 || hCountPos == 4) return true;

        if (c == r) {
            //checks diagonals within a grid
            int dCountNeg = 0;
            int dCountPos = 0;
            for (int i = 0; i <= 3; i++) {
                if (gameState[g][i][i] == player) dCountPos++;
                if (gameState[g][i][3-i] == player) dCountNeg++;
            }
            if (dCountNeg == 4 || dCountPos == 4) return true;

            //checks diagonals across grids
            int dGCountNeg = 0;
            int dGCountPos = 0;
            for (int i = 0; i <= 3; i++) {
                if (gameState[i][i][i] == player) dGCountPos++;
                if (gameState[i][i][3-i] == player) dGCountNeg++;
            }
            if (dGCountNeg == 4 || dGCountPos == 4) return true;

        }


        return false;
    }

    public void playAgain() {
        playCount = 0;
        activePlayer = true;

        //resets the game state to all blank
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    gameState[i][j][k] = 0;
                    buttons[i][j][k].setText("");
                }
            }
        }
    }




}

