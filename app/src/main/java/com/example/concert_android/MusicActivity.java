package com.example.concert_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        final ImageButton playButton = findViewById(R.id.playButton);
        final ImageButton skipNextButton = findViewById(R.id.skipNextButton);
        final ImageButton skipBackButton = findViewById(R.id.skipPreviousButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change image of play button to pause button after it is clicked
                // this requires API level 16 minimum
                //playButton.setBackground(R.drawable.ic_pause_black_24dp);
            }
        });

        skipNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        skipBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
