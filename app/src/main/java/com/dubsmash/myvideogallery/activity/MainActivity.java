package com.dubsmash.myvideogallery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.dubsmash.myvideogallery.R;
import com.dubsmash.myvideogallery.adapter.VideoAdapter;
import com.dubsmash.myvideogallery.camera.RecordVideo;
import com.dubsmash.myvideogallery.database.DatabaseUtil;
import com.dubsmash.myvideogallery.interfaces.ActivityCallBack;
import com.dubsmash.myvideogallery.model.VideoInformation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCallBack {

    private RecordVideo recordVideo;
    private VideoAdapter videoAdapter;
    private final ArrayList<VideoInformation> videoInformations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initCamera();
        retrieveVideos();
    }

    private void retrieveVideos() {
        videoInformations.clear();
        videoInformations.addAll(DatabaseUtil.getAllVideos(this));
        videoAdapter.notifyDataSetChanged();
    }

    private void initCamera() {
        recordVideo = new RecordVideo(this,this);
    }


    private void initViews() {
        final FloatingActionButton captureVideoFloatingActionButton = (FloatingActionButton) findViewById(R.id.captureVideoFloatingActionButton);
        captureVideoFloatingActionButton.setOnClickListener(captureVideoOnClickListener);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.videosRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(videoInformations,this);
        recyclerView.setAdapter(videoAdapter);
    }


    private final View.OnClickListener captureVideoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showVideoInformationDialog();
        }
    };

    private void showVideoInformationDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.enter_video_title);
        final EditText editText = new EditText(this);
        alertDialogBuilder.setView(editText);
        alertDialogBuilder.setPositiveButton(R.string.btn_record, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recordVideo.initCamera(editText.getText().toString());
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recordVideo.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recordVideo.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onVideoSaved(VideoInformation videoInformation) {
        videoInformations.add(videoInformation);
        videoAdapter.notifyItemInserted(videoInformations.size()-1);
    }

    @Override
    public void onItemClick(int position) {

        VideoInformation videoInformation = videoInformations.get(position);
        Intent intent = new Intent(this,VideoActivity.class);
        intent.putExtra("VideoPath",videoInformation.getPath());
        startActivity(intent);

    }
}
