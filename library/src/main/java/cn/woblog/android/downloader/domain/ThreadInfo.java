package cn.woblog.android.downloader.domain;

import java.io.Serializable;

/**
 * Created by renpingqing on 17/1/22.
 */

public class ThreadInfo implements Serializable {

  private int id;
  private int key;
  private String uri;
  private long start;
  private long end;
  private long progress;

  public ThreadInfo(int id, int key, String uri, long start, long end) {
    this.id = id;
    this.key = key;
    this.uri = uri;
    this.start = start;
    this.end = end;
  }

  public ThreadInfo() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
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
