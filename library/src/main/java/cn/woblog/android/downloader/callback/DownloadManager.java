package cn.woblog.android.downloader.callback;

import cn.woblog.android.downloader.domain.Download;

/**
 * Created by renpingqing on 15/01/2017.
 */

public interface DownloadManager {

  void download(Download download);

  void pause(Download download);

  void resume(Download download);

  void remove(Download download);

  void onDestroy();

  Download getDownloadById(String id);

}
