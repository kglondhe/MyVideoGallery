package com.dubsmash.myvideogallery.interfaces;

import com.dubsmash.myvideogallery.model.VideoInformation;

public interface ActivityCallBack {
        void onVideoSaved(VideoInformation videoInformation);

        void onItemClick(int position);
}