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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DataBinding
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.setOnClickListener(this);
        viewDataBinding.setViewModel(viewModel);

        // Setup Listeners
        viewModel.getmSocket().on("connected", viewModel.connected_callback);
        viewModel.getmSocket().on("heartbeat", viewModel.connected_callback);
        viewModel.getmSocket().on("played", viewModel.connected_callback);
        viewModel.getmSocket().on("volume_changed", viewModel.volume_callback);
        viewModel.getmSocket().on("paused", viewModel.pause_callback);

        //init
        viewModel.getmSocket().connect();
        viewModel.currentSong.setValue("no song playing");

        //hide title
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bt_login) {
            Intent intent = new Intent();
            intent.setClass(view.getContext(),LoginActivity.class);
            startActivity(intent);
        }

        if (view.getId() == R.id.playButton) {
            Log.e("Info",viewModel.getConcertStatus().toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.getmSocket().off();
        viewModel.getmSocket().close();
    }

}
