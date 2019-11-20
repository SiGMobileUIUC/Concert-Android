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
        playButton.setTag(0);
        final ImageButton skipNextButton = findViewById(R.id.skipNextButton);
        final ImageButton skipBackButton = findViewById(R.id.skipPreviousButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if the tag of playButton is 0 set the image to the pause
                // and update the tag, otherwise set the image to play and update the tag
                if (playButton.getTag() == (Integer)0) {
                    playButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    playButton.setTag(1);
                } else if (playButton.getTag() == (Integer)1) {
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    playButton.setTag(0);
                }

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
