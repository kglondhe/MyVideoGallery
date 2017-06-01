package com.dubsmash.myvideogallery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kapillondhe on 01/06/17.
 */

public class DatabaseLayer extends SQLiteOpenHelper{

    private static DatabaseLayer databaseLayer;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DUBSMASH_VIDEOS.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VideoDatabaseEntries.TABLE_NAME + " (" +
                    VideoDatabaseEntries._ID + " INTEGER PRIMARY KEY," +
                    VideoDatabaseEntries.COLUMN_NAME_VIDEO_TITLE + " TEXT," +
                    VideoDatabaseEntries.COLUMN_NAME_TIME + " LONG," +
                    VideoDatabaseEntries.COLUMN_NAME_VIDEO_PATH + " TEXT," +
                    VideoDatabaseEntries.COLUMN_NAME_DURATION + " LONG)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VideoDatabaseEntries.TABLE_NAME;

    public static DatabaseLayer getInstance(Context context){
        if(databaseLayer == null) {
            databaseLayer = new DatabaseLayer(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return databaseLayer;
    }

    public DatabaseLayer(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


    public static class VideoDatabaseEntries implements BaseColumns {
        public static final String TABLE_NAME = "videos";
        public static final String COLUMN_NAME_VIDEO_TITLE = "video_title";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_VIDEO_PATH = "video_path";
    }

}
