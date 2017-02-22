package cn.woblog.android.downloader.callback;

import java.lang.ref.SoftReference;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadListener {

  private SoftReference<Object> userTag;

  public DownloadListener() {
  }

  public DownloadListener(SoftReference<Object> userTag) {
    this.userTag = userTag;
  }

  public SoftReference<Object> getUserTag() {
    return userTag;
  }

  public void setUserTag(SoftReference<Object> userTag) {
    this.userTag = userTag;
  }

  public void onStart() {
  }

  public void onWaited() {
  }

  public void onPaused() {
  }

  public void onDownloading() {
  }

  public void onRemoved() {
  }

  public void onDownloadSuccess() {

  }

  public void onDownloadFailed() {

  }
}
