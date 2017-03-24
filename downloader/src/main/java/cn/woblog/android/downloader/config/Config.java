package cn.woblog.android.downloader.config;

/**
 * Created by renpingqing on 17/3/2.
 */

import cn.woblog.android.downloader.db.DownloadDBController;

/**
 * Download manager config.
 *
 * Can configure Timeout,Concurrent downloads task number, Each Download thread number
 */
public class Config {

  private final String method = "GET";
  private int connectTimeout = 10000;
  private int readTimeout = 10000;
  private int downloadThread = 2;
  private int eachDownloadThread = 2;
  private String databaseName = "download_info.db";
  //  private String databaseName = "/sdcard/d/download_info.db";
  private int databaseVersion = 2;
  private int retryDownloadCount = 2;
  private DownloadDBController downloadDBController;

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  public int getDownloadThread() {
    return downloadThread;
  }

  public void setDownloadThread(int downloadThread) {
    this.downloadThread = downloadThread;
  }

  public int getEachDownloadThread() {
    return eachDownloadThread;
  }

  public void setEachDownloadThread(int eachDownloadThread) {
    this.eachDownloadThread = eachDownloadThread;
  }

  public String getMethod() {
    return method;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public int getDatabaseVersion() {
    return databaseVersion;
  }

  public void setDatabaseVersion(int databaseVersion) {
    this.databaseVersion = databaseVersion;
  }

  public int getRetryDownloadCount() {
    return retryDownloadCount;
  }

  public void setRetryDownloadCount(int retryDownloadCount) {
    this.retryDownloadCount = retryDownloadCount;
  }

  public DownloadDBController getDownloadDBController() {
    return downloadDBController;
  }

  public void setDownloadDBController(
      DownloadDBController downloadDBController) {
    this.downloadDBController = downloadDBController;
  }
}
