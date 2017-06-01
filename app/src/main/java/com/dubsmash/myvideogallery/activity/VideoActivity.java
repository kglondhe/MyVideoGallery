package com.dubsmash.myvideogallery.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.dubsmash.myvideogallery.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);

        final String videoPath = getIntent().getStringExtra("VideoPath");
        final VideoView videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }
}
