package com.example.myminigames;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            final Button buttonMorpion = findViewById(R.id.button0);
            buttonMorpion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    //setContentView(R.layout.activity_morpion);
                    launchActivity();
                }
            });

            final Button buttonMemory = findViewById(R.id.button1);
            buttonMemory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    //setContentView(R.layout.activity_memory);
                    launchActivity2();
                }
            });

            final Button buttonPuissance = findViewById(R.id.button2);
            buttonPuissance.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    //setContentView(R.layout.activity_memory);
                    launchActivity3();
                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void launchActivity() {

        Intent intent = new Intent(this,  Morpion.class);
        startActivity(intent);
    }

    private void launchActivity2() {

        Intent intent = new Intent(this,  Memory.class);
        startActivity(intent);
    }

    private void launchActivity3() {

        Intent intent = new Intent(this,  Puissance4.class);
        startActivity(intent);
    }
}