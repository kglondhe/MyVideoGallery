package com.dubsmash.myvideogallery.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static com.dubsmash.myvideogallery.constants.CameraConstants.CAMERA_CALL_BACK_CODE;
import static com.dubsmash.myvideogallery.constants.CameraConstants.CAMERA_PERMISSION_CODE;

/**
 * Created by kapillondhe on 31/05/17.
 */

public class RecordVideo {

    final Activity activity;
    public RecordVideo(Activity activity){
        this.activity = activity;
    }
    public void initCamera(){

        if(isCameraPermissionAvailable()){
            openCameraForRecordingVideo();
        }else{
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_CONTACTS)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private void openCameraForRecordingVideo() {
        final Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeVideoIntent, CAMERA_CALL_BACK_CODE);
        }
    }

    private boolean isCameraPermissionAvailable(){
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera();
                } else {

                }

                break;
        }
    }


}
