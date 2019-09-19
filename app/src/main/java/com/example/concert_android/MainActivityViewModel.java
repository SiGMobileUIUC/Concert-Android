package com.example.concert_android;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivityViewModel {

    private Socket mSocket;
    private ConcertStatus concertStatus = new ConcertStatus();

    public MutableLiveData<String> currentSong = new MutableLiveData<>();

    {
        try {
            mSocket = IO.socket("https://concert.acm.illinois.edu/");
            Log.e("server","connected");

        } catch (
                URISyntaxException e) {

        }
    }

    public Socket getmSocket() {
        return mSocket;
    }

    public ConcertStatus getConcertStatus() {
        return concertStatus;
    }

    public final Emitter.Listener connected_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (args.length > 1) {
                concertStatus = new ConcertStatus((String) args[1]);
            } else {
                concertStatus = new ConcertStatus((String) args[0]);
            }
        }
    };

    public final Emitter.Listener volume_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject jsonObject = new JSONObject((String) args[0]);
                concertStatus.setVolume(jsonObject.getInt("volume"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public final Emitter.Listener pause_callback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Object arg : args) {
                Log.e("PAUSE", (String) arg);
            }
            try {
                JSONObject jsonObject = new JSONObject((String) args[0]);
                concertStatus.setAudioStatus(jsonObject.getString("audio_status"));
                concertStatus.setCurrentTime(jsonObject.getInt("current_time"));
                concertStatus.setDuration(jsonObject.getInt("duration"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
