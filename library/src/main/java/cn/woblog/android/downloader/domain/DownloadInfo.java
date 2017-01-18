package cn.woblog.android.downloader.domain;

/**
 * Created by renpingqing on 15/01/2017.
 */

public class DownloadInfo {

  private String downloadId;
  private long createAt;
  private String url;
  private String path;
  private long size;
  private long progress;
  private String name;

  public String getDownloadId() {
    return downloadId;
  }

  public void setDownloadId(String downloadId) {
    this.downloadId = downloadId;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DownloadInfo that = (DownloadInfo) o;

    if (createAt != that.createAt) {
      return false;
    }
    if (size != that.size) {
      return false;
    }
    if (progress != that.progress) {
      return false;
    }
    if (!downloadId.equals(that.downloadId)) {
      return false;
    }
    if (!url.equals(that.url)) {
      return false;
    }
    if (!path.equals(that.path)) {
      return false;
    }
    return name != null ? name.equals(that.name) : that.name == null;

  }

  @Override
  public int hashCode() {
    int result = downloadId.hashCode();
    result = 31 * result + (int) (createAt ^ (createAt >>> 32));
    result = 31 * result + url.hashCode();
    result = 31 * result + path.hashCode();
    result = 31 * result + (int) (size ^ (size >>> 32));
    result = 31 * result + (int) (progress ^ (progress >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }


}
