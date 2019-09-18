package com.example.concert_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.concert_android.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MainActivityViewModel viewModel = new MainActivityViewModel();
    private Socket mSocket;
    private ConcertStatus currStatus;

    {
        try {
            mSocket = IO.socket("https://concert.acm.illinois.edu/");
        } catch (URISyntaxException e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.setOnClickListener(this);

        //init
        currStatus = new ConcertStatus();

        //init socket
        mSocket.connect();
        try {
            mSocket = IO.socket("https://concert.acm.illinois.edu/");
        } catch (URISyntaxException e) {

        }

        // Setup Listeners
        mSocket.on("connected", connected_callback);
        mSocket.on("heartbeat", connected_callback);
        mSocket.on("played", connected_callback);
        mSocket.on("volume_changed", volume_callback );
        mSocket.on("paused", pause_callback);

        //hide title
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    private final Emitter.Listener connected_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            final ConcertStatus status;

            if (args.length > 1) {
                status = new ConcertStatus((String) args[1]);
            } else {
                status = new ConcertStatus((String) args[0]);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //updateUI(status);
                }
            });
        }
    };

    private final Emitter.Listener volume_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject jsonObject = new JSONObject((String) args[0]);
                currStatus.setVolume(jsonObject.getInt("volume"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Emitter.Listener pause_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Object arg : args) {
                Log.e("PAUSE", (String) arg);
            }
            try {
                JSONObject jsonObject = new JSONObject((String) args[0]);
                currStatus.setAudioStatus(jsonObject.getString("audio_status"));
                currStatus.setCurrentTime(jsonObject.getInt("current_time"));
                currStatus.setDuration(jsonObject.getInt("duration"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bt_login) {
            Intent intent = new Intent();
            intent.setClass(view.getContext(),LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.off();
        mSocket.close();
    }

}
