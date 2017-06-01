package com.dubsmash.myvideogallery.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.dubsmash.myvideogallery.R;
import com.dubsmash.myvideogallery.database.DatabaseUtil;
import com.dubsmash.myvideogallery.interfaces.ActivityCallBack;
import com.dubsmash.myvideogallery.model.VideoInformation;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static android.app.Activity.RESULT_OK;
import static com.dubsmash.myvideogallery.constants.CameraConstants.CAMERA_CALL_BACK_CODE;
import static com.dubsmash.myvideogallery.constants.CameraConstants.CAMERA_PERMISSION_CODE;

/**
 * Created by kapillondhe on 31/05/17.
 */

public class RecordVideo {

    final Activity activity;
    final ActivityCallBack activityCallBack;

    public RecordVideo(Activity activity, ActivityCallBack activityCallBack) {
        this.activity = activity;
        this.activityCallBack = activityCallBack;
    }

    private String videoTitle;
    private String videoPath;

    public void initCamera(String videoTitle) {

        this.videoTitle = videoTitle;
        if (isCameraPermissionAvailable()) {
            openCameraForRecordingVideo();
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {

            Toast.makeText(activity, R.string.permission_warning, Toast.LENGTH_SHORT).show();

        } else {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_CODE);
        }
    }

    private void openCameraForRecordingVideo() {
        final Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        final File sdCard = Environment.getExternalStorageDirectory();
        final File capturedVideo = new File(sdCard, String.format("%s/%s%d.mp4", activity.getFilesDir().getAbsolutePath(), videoTitle.replaceAll("\\s+", ""), System.currentTimeMillis()));
        capturedVideo.mkdirs();
        try {
            capturedVideo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoPath = capturedVideo.getAbsolutePath();
        final URI uri = URI.create(videoPath);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeVideoIntent, CAMERA_CALL_BACK_CODE);
        }
    }

    private boolean isCameraPermissionAvailable() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_CALL_BACK_CODE:
                if (resultCode == RESULT_OK) {
                    saveVideoInformation(data);
                }
                break;

        }
    }

    private void saveVideoInformation(Intent data) {
        final VideoInformation videoInformation = new VideoInformation();
        videoInformation.setVideoTitle(TextUtils.isEmpty(videoTitle) ? activity.getString(R.string.default_name) : videoTitle);
        videoInformation.setPath(data.getData().toString());
        videoInformation.setTime(System.currentTimeMillis());
        videoInformation.setDuration(getVideoDurationFromIntent(data));
        DatabaseUtil.saveVideoInformation(activity, videoInformation);
        activityCallBack.onVideoSaved(videoInformation);
    }

    private int getVideoDurationFromIntent(Intent data) {
        final Cursor cursor = MediaStore.Video.query(activity.getContentResolver(), data.getData(),
                new String[]{MediaStore.Video.VideoColumns.DURATION});

        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera(videoTitle);
                } else {
                    Toast.makeText(activity, R.string.permission_warning, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


}
