package com.example.myminigames;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Memory extends AppCompatActivity {

    ImageView first = null;
    ImageView second = null;
    private int count = 0;
    private int turn;
    private int count1;
    private int count2;
    private TextView p1;
    private TextView p2;


    final int[] array = new int[]{
            R.drawable.img_7_r,
            R.drawable.img_8_r,
            R.drawable.img_9_r,
            R.drawable.img_10_r,
            R.drawable.img_as_r,
            R.drawable.img_jack_r,
            R.drawable.img_queen_r,
            R.drawable.img_king_r
    };

    int[] pos = { 0, 1, 2, 3, 4, 5, 6, 7,
            0, 1, 2, 3, 4, 5, 6, 7 };

    int current = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        p1 = findViewById(R.id.player1);
        p2 = findViewById(R.id.player2);
        p1.setTextColor(Color.BLACK);
        p2.setTextColor(Color.GRAY);
        count1 = 0;
        count2 = 0;
        turn = 1;

        GridView grid = findViewById(R.id.grid);
        Images i = new Images(this);
        grid.setAdapter(i);
        shuffle();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(current < 0){
                    current = position;
                    first = (ImageView) view;
                    ((ImageView)view).setImageResource(array[pos[position]]);
                }
                else {
                    if(current == position){
                        ((ImageView)view).setImageResource(R.drawable.dos);
                        if(turn == 1){
                            turn = 2;
                            p1.setTextColor(Color.GRAY);
                            p2.setTextColor(Color.BLACK);
                        }
                        else{
                            turn = 1;
                            p2.setTextColor(Color.GRAY);
                            p1.setTextColor(Color.BLACK);
                        }
                    }
                    else if(pos[current] != pos[position]){
                        first.setImageResource(R.drawable.dos);
                        Toast.makeText(getApplicationContext(), "Ce n'est pas celle ci", Toast.LENGTH_SHORT).show();
                        if(turn == 1){
                            turn = 2;
                            p1.setTextColor(Color.GRAY);
                            p2.setTextColor(Color.BLACK);
                        }
                        else{
                            turn = 1;
                            p2.setTextColor(Color.GRAY);
                            p1.setTextColor(Color.BLACK);
                        }
                    }
                    else{
                        ((ImageView)view).setImageResource(array[pos[position]]);
                        if(turn == 1){
                            count1++;
                        } else count2++;
                        count++;
                        p1.setText("Joueur 1 : " + count1);
                        p2.setText("Joueur 2 : " + count2);
                        Toast.makeText(getApplicationContext(), "Joli coup !", Toast.LENGTH_SHORT).show();

                        if(count == 8){
                            end();
                        }
                    }
                    current = -1;
                }
            }
        });

        final Button buttonBack = findViewById(R.id.back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //setContentView(R.layout.activity_morpion);
                launchActivity();
            }
        });
    }

    public void shuffle(){
        for(int i = pos.length-1; i >= 1; i--){

            int j = (int) Math.floor(Math.random()*(i + 1));

            //Echange
            int tmp = pos[i];
            pos[i] = pos[j];
            pos[j] = tmp ;

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

    public void end(){
        if(count1 > count2) endGameAlert("Joueur 1 a gagné avec " + count1 + " points");
        else if(count2 > count1) endGameAlert("Joueur 2 a gagné avec " + count2 + " points");
        else endGameAlert("Egalité");
    }

    public void reset() {
        GridView grid = findViewById(R.id.grid);
        Images i = new Images(this);
        grid.setAdapter(i);
    }

    private void launchActivity() {

        Intent intent = new Intent(this,  MainActivity.class);
        startActivity(intent);
    }
}
