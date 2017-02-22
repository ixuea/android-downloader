package cn.woblog.android.downloader.simple.callback;

import cn.woblog.android.downloader.callback.DownloadListener;
import java.lang.ref.SoftReference;

/**
 * Created by renpingqing on 17/1/22.
 */

public abstract class MyDownloadListener extends DownloadListener {

  public MyDownloadListener() {
    super();
  }

  public MyDownloadListener(SoftReference<Object> userTag) {
    super(userTag);
  }

  @Override
  public void onStart() {
    super.onStart();
    onRefresh();
  }

  public abstract void onRefresh();

  @Override
  public void onWaited() {
    super.onWaited();
    onRefresh();
  }

  @Override
  public void onDownloading() {
    super.onDownloading();
    onRefresh();
  }

  @Override
  public void onRemoved() {
    super.onRemoved();
    onRefresh();
  }

  @Override
  public void onDownloadSuccess() {
    super.onDownloadSuccess();
    onRefresh();
  }

  @Override
  public void onDownloadFailed() {
    super.onDownloadFailed();
    onRefresh();
  }
}
