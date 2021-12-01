package com.example.rhythm_n_reps;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

//GOSPEL https://github.com/google/ExoPlayer/releases

//https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/source/ProgressiveMediaSource.html

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//Deprecate SimpleExoPlayer. All functionality has been moved to ExoPlayer instead.
// most recent 11/24: ExoPlayer.Builder >> SimpleExoPlayer.Builder >> ExoPlayerFactory
// 11/24: Exoplayer >> SimpleExoPlayer
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayer.Builder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaParserExtractorAdapter;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.example.rhythm_n_reps.R;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;

import java.io.File;
import java.util.SimpleTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
//testing Exoplayer functionality
//some code from https://exoplayer.dev/hello-world.html


public class WWorkshopActivity extends AppCompatActivity {
    //queen
//    String videoUrl = "https://drive.google.com/file/d/17Jd3GGGBrp8GWEAQW9C1Ti5xkQT5uLzQ/view?usp=sharing";

    PlayerView pvMain;
    ExoPlayer player;
//    RENDERER_COUNT = 1 //since you want to render simple audio
//    minBufferMs = 1000
//    minRebufferMs = 5000


    String videoUri = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wworkshop);

        player = new ExoPlayer.Builder(this).build();
//        DataSource dataSource = new DefaultUriDataSource

//        playerView = findViewById(R.id.video_view);
////        player = new ExoPlayer.Builder(this).build();
//
////        initializePlayer();
//        // Build the media item.
//        MediaItem mediaItem = MediaItem.fromUri(videoUri);
//// Set the media item to be played.
//        player.setMediaItem(mediaItem);
//// Prepare the player.
//        player.prepare();
//// Start the playback.
//        player.play();

    }

//    Builder(Context context, RenderersFactory renderersFactory, MediaSourceFactory mediaSourceFactory)

//    @RequiresApi(api = Build.VERSION_CODES.R)
//    private void initializePlayer() {
//        ExoPlayer player = new Builder(this, new DefaultRenderersFactory(this)).build();
////        https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/ExoPlayer.Builder.html
////        ExoPlayer player = new Builder(this,
////                new DefaultRenderersFactory(this),
////                new DefaultTrackSelector(this),
////                new DefaultLoadControl()).build();
//
//        String filePath = Environment.getExternalStorageDirectory() + File.separator +
//                "video" + File.separator + "video1.mp4";
//        Log.e("filepath", filePath);
//        Uri uri = Uri.parse(filePath);
//
//        //ProgressiveMediaSource >> ExtractorMediaSource
//        // "Provides one period that loads data from a Uri and extracted using an Extractor"
//
//
//        ProgressiveMediaSource audioSource = new ProgressiveMediaSource(
//                uri,
//                new DefaultDataSource.Factory(this),
//                new MediaParserExtractorAdapter(),
//                null,
//                null, null
//        );
//
//        player.prepare(audioSource);
//        playerView.setPlayer(player);
//        player.setPlayWhenReady(true);
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        startPlayer();
//    }
//
//    @Override
//    protected void onPause() {
//        release();
//        super.onPause();
//    }


//    "In all cases the Looper of the thread from which the player must be
//    accessed can be queried using Player.getApplicationLooper."

    //Create view & attach player to a view

    //remember to release ExoPlayer w/ ExoPlayer.release
}