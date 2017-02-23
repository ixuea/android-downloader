package cn.woblog.android.downloader.callback;

import java.lang.ref.SoftReference;

/**
 * Created by renpingqing on 17/1/22.
 */

public abstract class AbsDownloadListener implements DownloadListener {

  private SoftReference<Object> userTag;

  public AbsDownloadListener() {
  }

  public AbsDownloadListener(SoftReference<Object> userTag) {
    this.userTag = userTag;
  }

  public SoftReference<Object> getUserTag() {
    return userTag;
  }

  public void setUserTag(SoftReference<Object> userTag) {
    this.userTag = userTag;
  }


}
