package com.example.myminigames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class Morpion extends AppCompatActivity {

    private char         player     = 'X';
    private TextView     playerTurn = null;
    private char         grid[][]   = new char[3][3];
    private List<Button> buttons    = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);

        try
        {
            playerTurn = (TextView) findViewById(R.id.player);

            // Honnêtement je sais pas comment faire mieux que ça
            initButton(R.id.button00, 0, 0);
            initButton(R.id.button01, 0, 1);
            initButton(R.id.button02, 0, 2);
            initButton(R.id.button10, 1, 0);
            initButton(R.id.button11, 1, 1);
            initButton(R.id.button12, 1, 2);
            initButton(R.id.button20, 2, 0);
            initButton(R.id.button21, 2, 1);
            initButton(R.id.button22, 2, 2);

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

    public void initButton(int buttonId, final int x, final int y) {
        Button button = (Button) findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grid[x][y] == '\0') // Permet d'éviter de cliquer sur une case déjà set
                {
                    ((Button) v).setText(getPlayer());
                    grid[x][y] = player;
                    update();
                }
            }
        });
        buttons.add(button);
    }

    public void update() {
        char   result   = win();
        if(result == 'n')
        {
            if(player == 'X')
            {
                player = 'O';
                setPlayerTurn(2);
            } else
            {
                player = 'X';
                setPlayerTurn(1);
            }
            return;
        }

        switch(result) {
            case 'X':
                endGameAlert("Joueur 1 a gagné ");
                break;
            case 'O':
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
        for(int i = 0; i <= 2; i++)
        {
            if(grid[i][0] != NULL && grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2]) return grid[i][0];
        }

        for(int j = 0; j <= 2; j++)
        {
            if(grid[0][j] != NULL && grid[0][j] == grid[1][j] && grid[0][j] == grid[2][j]) return grid[0][j];
        }

        if(grid[0][0] != NULL && grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2]) return grid[0][0];

        if(grid[2][0] != NULL && grid[2][0] == grid[1][1] && grid[2][0] == grid[0][2]) return grid[2][0];

        boolean draw = true;
        for(int i = 0; i <= 2; i++)
        {
            for(int j = 0; j <= 2; j++)
            {
                if(grid[i][j] == NULL) draw = false;
            }
        }
        if(draw) return 'e';

        return 'n';
    }

    public void reset() {
        for(int i = 0; i <= 2; i++)
        {
            for(int j = 0; j <= 2; j++)
            {
                grid[i][j] = NULL;
            }
        }
        for(Button btn : buttons)
        {
            btn.setText("");
        }
        player = 'X';
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
}
