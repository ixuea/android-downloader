package cn.woblog.android.downloader.simple.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.List;

/**
 * Created by renpingqing on 17/3/2.
 */
@DatabaseTable(tableName = "MyDownloadInfLocal")
public class MyDownloadInfLocal {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField
  private long createAt;

  @DatabaseField
  private String uri;

  @DatabaseField
  private String path;

  @DatabaseField
  private long size;

  @DatabaseField
  private long progress;

  @DatabaseField
  private int status;

  @DatabaseField
  private int supportRanges;

  private List<MyDownloadThreadInfoLocal> downloadThreadInfos;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getProgress() {
    return progress;
  }

  public void setProgress(long progress) {
    this.progress = progress;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<MyDownloadThreadInfoLocal> getDownloadThreadInfos() {
    return downloadThreadInfos;
  }

  public void setDownloadThreadInfos(
      List<MyDownloadThreadInfoLocal> downloadThreadInfos) {
    this.downloadThreadInfos = downloadThreadInfos;
  }

  public int getSupportRanges() {
    return supportRanges;
  }

  public void setSupportRanges(int supportRanges) {
    this.supportRanges = supportRanges;
  }
}
