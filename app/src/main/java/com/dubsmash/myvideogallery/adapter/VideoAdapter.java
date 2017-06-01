package com.dubsmash.myvideogallery.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dubsmash.myvideogallery.R;
import com.dubsmash.myvideogallery.interfaces.ActivityCallBack;
import com.dubsmash.myvideogallery.model.VideoInformation;
import com.dubsmash.myvideogallery.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by kapillondhe on 01/06/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final ArrayList<VideoInformation> videoInformations;
    private final ActivityCallBack activityCallBack;

    public VideoAdapter(ArrayList<VideoInformation> videoInformations, ActivityCallBack activityCallBack) {
        this.videoInformations = videoInformations;
        this.activityCallBack = activityCallBack;

    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.video_item, parent, false);


        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {
        final VideoInformation videoInformation = videoInformations.get(position);
        holder.videoTitle.setText(videoInformation.getVideoTitle());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallBack.onItemClick(position);
            }
        });
        holder.videoDuration.setText(DateUtil.convertToHHMMSS(videoInformation.getDuration()));
        holder.videoDate.setText(DateUtil.convertToDate(videoInformation.getTime()));
        final Bitmap bitmap = getVideoThumbnail(getPathFromURI(holder.parentView.getContext(),videoInformation.getPath()));
        if (bitmap != null) {
            holder.videoThumbnail.setImageBitmap(bitmap);
        } else {
            holder.videoThumbnail.setImageResource(R.mipmap.ic_launcher);
        }

    }

    private Bitmap getVideoThumbnail(String path) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
        return bitmap;
    }

    private String getPathFromURI(Context context, String uriString) {
        final Uri uri = Uri.parse(uriString);
        final String[] filePathColumn = {MediaStore.Images.Media.DATA};
        final Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        String realPath = null;
        if (cursor != null && cursor.moveToFirst()) {
            final int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            realPath = cursor.getString(columnIndex);
            cursor.close();
        }

        return realPath;
    }

    @Override
    public int getItemCount() {
        return videoInformations.size();
    }
}
