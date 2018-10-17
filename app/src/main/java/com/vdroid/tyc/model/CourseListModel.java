package com.vdroid.tyc.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vdroid.tyc.database.AppDatabase;
import com.vdroid.tyc.database.entities.CourseEntity;

import java.util.List;

public class CourseListModel extends AndroidViewModel {

    LiveData<List<CourseEntity>> mCourses;

    public CourseListModel(@NonNull Application application) {
        super(application);
        this.mCourses = AppDatabase.getInstance(application).courseDao().loadAllCourses();
    }

    public LiveData<List<CourseEntity>> getCourses() {
        return mCourses;
    }
}
