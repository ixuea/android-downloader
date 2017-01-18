package cn.woblog.android.downloader.callback;

import cn.woblog.android.downloader.domain.DownloadInfo;

/**
 * Created by renpingqing on 15/01/2017.
 */

public interface DownloadManager {

  void download(DownloadInfo info);

  void pause(DownloadInfo info);

  void resume(DownloadInfo info);

  void remove(DownloadInfo info);

  void onDestroy();
}
