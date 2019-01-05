package com.ixuea.android.downloader.simple.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

@DatabaseTable(tableName = "MyDownloadThreadInfoLocal")
public class MyDownloadThreadInfoLocal implements Serializable {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private int threadId;

    @DatabaseField
    private String downloadInfoId;
    private String uri;

    @DatabaseField
    private long start;

    @DatabaseField
    private long end;

    @DatabaseField
    private long progress;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getDownloadInfoId() {
        return downloadInfoId;
    }

    public void setDownloadInfoId(String downloadInfoId) {
        this.downloadInfoId = downloadInfoId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
