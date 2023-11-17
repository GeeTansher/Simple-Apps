package com.example.playvideoexo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;


public class MainActivity extends AppCompatActivity {
    private PlayerView playerView;
    private DefaultTrackSelector defaultTrackSelector;
    private ExoPlayer player;
    private String videoURL = "https://buildappswithpaulo.com/videos/outro_music.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.exoPlayer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        defaultTrackSelector = new DefaultTrackSelector(this);
        player = new ExoPlayer.Builder(this)
                .setTrackSelector(defaultTrackSelector)
                .build();

        playerView.setPlayer(player);
        player.addMediaItem(1, MediaItem.fromUri(videoURL));
        player.prepare();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player=null;
    }
}