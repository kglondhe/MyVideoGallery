package com.dubsmash.myvideogallery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dubsmash.myvideogallery.model.VideoInformation;

import java.util.ArrayList;

/**
 * Created by kapillondhe on 01/06/17.
 */

public class DatabaseUtil {

    public static void saveVideoInformation(Context context,VideoInformation videoInformation){

        final SQLiteDatabase sqLiteDatabase  = DatabaseLayer.getInstance(context).getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_TIME,videoInformation.getTime());
        contentValues.put(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_DURATION,videoInformation.getDuration());
        contentValues.put(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_PATH,videoInformation.getPath());
        contentValues.put(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_TITLE,videoInformation.getVideoTitle());

        sqLiteDatabase.insert(DatabaseLayer.VideoDatabaseEntries.TABLE_NAME,null,contentValues);
    }

    public static ArrayList<VideoInformation> getAllVideos(Context context){

        final SQLiteDatabase sqLiteDatabase  = DatabaseLayer.getInstance(context).getReadableDatabase();
        final String[] projection = {
                DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_TIME,
                DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_DURATION,
                DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_PATH,
                DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_TITLE
        };

        final Cursor cursor = sqLiteDatabase.query(DatabaseLayer.VideoDatabaseEntries.TABLE_NAME,projection,null,null,null,null,null);

        final ArrayList<VideoInformation> videoInformations = new ArrayList<>();
        while(cursor.moveToNext()) {
            final VideoInformation videoInformation = new VideoInformation();
            videoInformation.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_TIME)));
            videoInformation.setDuration(cursor.getLong(cursor.getColumnIndex(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_DURATION)));
            videoInformation.setPath(cursor.getString(cursor.getColumnIndex(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_PATH)));
            videoInformation.setVideoTitle(cursor.getString(cursor.getColumnIndex(DatabaseLayer.VideoDatabaseEntries.COLUMN_NAME_VIDEO_TITLE)));
            videoInformations.add(videoInformation);
        }
        cursor.close();

        return videoInformations;
    }
}
