package cn.woblog.android.downloader;

import android.content.Context;
import cn.woblog.android.downloader.callback.IDownloadManager;
import cn.woblog.android.downloader.domain.DownloadInfo;

/**
 * Created by renpingqing on 14/01/2017.
 */

public class DownloadManager implements IDownloadManager {

  private static DownloadManager instance;

  public DownloadManager(Context context) {

  }

  public static DownloadManager getInstance(Context context) {
    synchronized (DownloadManager.class) {
      if (instance == null) {
        instance = new DownloadManager(context);
      }
    }
    return instance;
  }

  public void onDestroy() {

  }

  @Override
  public void download(DownloadInfo info) {

  }

  @Override
  public void pause(DownloadInfo info) {

  }

  @Override
  public void resume(DownloadInfo info) {

  }

  @Override
  public void remove(DownloadInfo info) {

  }
}
