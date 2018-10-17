package com.vdroid.tyc.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.vdroid.tyc.database.dao.CourseDao;
import com.vdroid.tyc.database.entities.CourseEntity;

@Database(entities = {CourseEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object sLock = new Object();
    private static AppDatabase sInstance;
    private static String DATABASE_NAME = "tyc_room.db";

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME
                )
                        .build();
            }
        }

        return sInstance;
    }

    public abstract CourseDao courseDao();

}
