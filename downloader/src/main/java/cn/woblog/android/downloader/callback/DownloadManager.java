package cn.woblog.android.downloader.callback;

import cn.woblog.android.downloader.domain.DownloadInfo;

/**
 * Created by renpingqing on 15/01/2017.
 */

public interface DownloadManager {

  void download(DownloadInfo downloadInfo);

  void pause(DownloadInfo downloadInfo);

  void resume(DownloadInfo downloadInfo);

  void remove(DownloadInfo downloadInfo);

  void onDestroy();

  DownloadInfo getDownloadById(int id);

}
