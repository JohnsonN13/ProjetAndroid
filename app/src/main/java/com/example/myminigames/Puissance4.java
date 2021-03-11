package com.example.myminigames;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class Puissance4 extends AppCompatActivity {

    private char         player     = 'J';
    private TextView     playerTurn = null;
    private char         grid[][]   = new char[4][7];
    private List<Button> buttons    = new ArrayList<>();
    private Button       tBtn[][] = new Button[4][7];
    private Button       btn;
    private int          x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puissance4);

        try
        {
            playerTurn = (TextView) findViewById(R.id.player);

            // Honnêtement je sais pas comment faire mieux que ça
            initButton(R.id.btn00, 0, 0);
            initButton(R.id.btn01, 0, 1);
            initButton(R.id.btn02, 0, 2);
            initButton(R.id.btn03, 0, 3);
            initButton(R.id.btn04, 0, 4);
            initButton(R.id.btn05, 0, 5);
            initButton(R.id.btn06, 0, 6);

            initButton(R.id.btn10, 1, 0);
            initButton(R.id.btn11, 1, 1);
            initButton(R.id.btn12, 1, 2);
            initButton(R.id.btn13, 1, 3);
            initButton(R.id.btn14, 1, 4);
            initButton(R.id.btn15, 1, 5);
            initButton(R.id.btn16, 1, 6);

            initButton(R.id.btn20, 2, 0);
            initButton(R.id.btn21, 2, 1);
            initButton(R.id.btn22, 2, 2);
            initButton(R.id.btn23, 2, 3);
            initButton(R.id.btn24, 2, 4);
            initButton(R.id.btn25, 2, 5);
            initButton(R.id.btn26, 2, 6);

            initButton(R.id.btn30, 3, 0);
            initButton(R.id.btn31, 3, 1);
            initButton(R.id.btn32, 3, 2);
            initButton(R.id.btn33, 3, 3);
            initButton(R.id.btn34, 3, 4);
            initButton(R.id.btn35, 3, 5);
            initButton(R.id.btn36, 3, 6);

            final Button buttonBack = findViewById(R.id.back);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    //setContentView(R.layout.activity_morpion);
                    launchActivity();
                }
            });


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void initButton(int buttonId, final int row, final int col) {
        final Button button = (Button) findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grid[0][col] == '\0')
                {
                    x1 = find(col);
                    btn = tBtn[x1][col];
                    if(player == 'J') {
                        btn.setBackgroundColor(Color.YELLOW);
                        grid[x1][col] = player;
                    } else if(player == 'R') {
                        btn.setBackgroundColor(Color.RED);
                        grid[x1][col] = player;
                    }
                    update();
                }
            }
        });
        button.setBackgroundResource(android.R.drawable.btn_default);
        buttons.add(button);
        tBtn[row][col] = button;
    }

    public void update() {
        char  result   = win();
        if(result == 'n') {
            if (player == 'J') {
                player = 'R';
                setPlayerTurn(2);
            } else {
                player = 'J';
                setPlayerTurn(1);
            }
            return;
        }

        switch(result) {
            case 'J':
                endGameAlert("Joueur Jaune a gagné ");
                break;
            case 'R':
                endGameAlert("Joueur 2 a gagné ");
                break;
            case 'e':
                endGameAlert("Egalité ");
                break;
        }
    }

    public void endGameAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Fin de la partie");
        alert.setMessage(message);
        alert.setNeutralButton("Rejouer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        });
        alert.show();
    }

    public char win() {

        //lignes
        for(int i = 0; i <= 3; i++)
        {
            char tmp;
            for(int j = 0; j <= 3; j++){
                tmp = grid[i][j];
                if(tmp != NULL && tmp == grid[i][j+1] && tmp == grid[i][j+2] && tmp == grid[i][j+3]) return tmp;
            }
        }

        //colonnes
        for(int k = 0; k <= 6; k++)
        {
            char tmp;
            tmp = grid[0][k];
            if(tmp != NULL && tmp == grid[1][k] && tmp == grid[2][k] && tmp == grid[3][k]) return tmp;
        }

        //diagonales

        /*4 montantes :
        3 0, 2 1, 1 2, 0 3
        3 1, 2 2, 1 3, 0 4
        3 2, 2 3, 1 4, 0 5,
        3 3, 2 4, 1 5, 0 6
        */

        int dj = 0;
        int di = 3;
        for(dj = 0; dj <= 3; dj++){
            char tmp;
            tmp = grid[di][dj];
            if(tmp != NULL && tmp == grid[di][dj] && tmp == grid[di-1][dj+1] && tmp == grid[di-2][dj+2] && tmp == grid[di-3][dj+3]) return tmp;
        }

         /*4 descendantes :
         0 0, 1 1, 2 2, 3 3
         0 1, 1 2, 2 3, 3 4
         0 2, 1 3, 2 4, 3 5
         0 3, 1 4, 2 5, 3 6
         */

        int dk = 0;
        int dl = 0;
        for(dk = 0; dk <= 3; dk++){
            char tmp;
            tmp = grid[dl][dk];
            if(tmp != NULL && tmp == grid[dl][dk] && tmp == grid[dl+1][dk+1] && tmp == grid[dl+2][dk+2] && tmp == grid[dl+3][dk+3]) return tmp;
        }

        boolean draw = true;
        for(int i = 0; i <= 3; i++)
        {
            for(int j = 0; j <= 6; j++)
            {
                if(grid[i][j] == NULL) draw = false;
            }
        }
        if(draw) return 'e';

        return 'n';
    }

    public void reset() {
        for(int i = 0; i <= 3; i++)
        {
            for(int j = 0; j <= 6; j++)
            {
                grid[i][j] = NULL;
            }
        }
        for(Button btn : buttons)
        {
            btn.setBackgroundResource(android.R.drawable.btn_default);;
        }
        player = 'J';
        setPlayerTurn(1);
    }

    public void setPlayerTurn(int player) {
        StringBuilder str = new StringBuilder();
        str.append("Joueur ").append(player).append(" à toi de jouer!");
        playerTurn.setText(str.toString());
    }

    public String getPlayer() {
        return player + "";
    }

    private void launchActivity() {

        Intent intent = new Intent(this,  MainActivity.class);
        startActivity(intent);
    }

    public int find(int y){
        for(int i = 3; i >= 0; i--){
            if(grid[i][y] == '\0'){
                return i;
            }
        }
        return -1;
    }
}
