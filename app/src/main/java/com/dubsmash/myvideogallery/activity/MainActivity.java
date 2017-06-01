package com.dubsmash.myvideogallery.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dubsmash.myvideogallery.R;
import com.dubsmash.myvideogallery.camera.RecordVideo;

import static com.dubsmash.myvideogallery.constants.CameraConstants.CAMERA_CALL_BACK_CODE;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton captureVideoFloatingActionButton;
    private RecordVideo recordVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initCamera();
    }

    private void initCamera() {
        recordVideo = new RecordVideo(this);
    }


    private void initViews() {
        captureVideoFloatingActionButton = (FloatingActionButton) findViewById(R.id.captureVideoFloatingActionButton);
        captureVideoFloatingActionButton.setOnClickListener(captureVideoOnClickListener);
    }

    private View.OnClickListener captureVideoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recordVideo.initCamera();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_CALL_BACK_CODE:
                if(resultCode == RESULT_OK){

                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recordVideo.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
