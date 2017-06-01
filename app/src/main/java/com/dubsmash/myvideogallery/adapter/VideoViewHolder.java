package com.dubsmash.myvideogallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dubsmash.myvideogallery.R;

/**
 * Created by kapillondhe on 01/06/17.
 */

class VideoViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout parentView;
    ImageView videoThumbnail;
    TextView videoTitle;
    TextView videoDuration;
    TextView videoDate;
    VideoViewHolder(View itemView) {
        super(itemView);
        parentView = (RelativeLayout) itemView.findViewById(R.id.parent);
        videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
        videoDate = (TextView) itemView.findViewById(R.id.video_date);
        videoDuration = (TextView) itemView.findViewById(R.id.video_duration);
        videoTitle = (TextView) itemView.findViewById(R.id.video_title);
    }
}
