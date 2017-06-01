package com.dubsmash.myvideogallery.model;

/**
 * Created by kapillondhe on 01/06/17.
 */

public class VideoInformation {

    private long time;
    private long duration;
    private String path;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
