package cn.woblog.android.downloader.domain;

import static android.R.attr.key;

import java.io.Serializable;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadThreadInfo implements Serializable {

  private int threadId;
  private int downloadKey;
  private String uri;
  private long start;
  private long end;
  private long progress;

  public DownloadThreadInfo(int threadId, int downloadKey, String uri, long start, long end) {
    this.threadId = threadId;
    this.downloadKey = key;
    this.uri = uri;
    this.start = start;
    this.end = end;
  }

  public DownloadThreadInfo() {
  }

  public int getThreadId() {
    return threadId;
  }

  public void setThreadId(int threadId) {
    this.threadId = threadId;
  }

  public int getDownloadKey() {
    return downloadKey;
  }

  public void setDownloadKey(int downloadKey) {
    this.downloadKey = downloadKey;
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
