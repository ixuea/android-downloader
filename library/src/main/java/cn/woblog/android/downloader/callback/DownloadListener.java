package cn.woblog.android.downloader.callback;

/**
 * Created by renpingqing on 17/2/23.
 */

public interface DownloadListener {

  void onStart();

  void onWaited();

  void onPaused();

  void onDownloading();

  void onRemoved();

  void onDownloadSuccess();

  void onDownloadFailed();
}
