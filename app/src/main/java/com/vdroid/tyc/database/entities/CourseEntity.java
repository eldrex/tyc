package com.vdroid.tyc.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "courses", indices = {
        @Index(name = "fileUriUnique", unique = true, value = "name")
})
public class CourseEntity {


    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "file_uri")
    private String fileUri;
    @ColumnInfo(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
