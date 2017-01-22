package cn.woblog.android.downloader;

import android.content.Context;
import cn.woblog.android.downloader.callback.DownloadManager;
import cn.woblog.android.downloader.domain.Download;

/**
 * Created by renpingqing on 14/01/2017.
 */

public final class DownloadManagerImpl implements DownloadManager {

  private static DownloadManagerImpl instance;

  private DownloadManagerImpl(Context context) {

  }

  public static DownloadManager getInstance(Context context) {
    synchronized (DownloadManagerImpl.class) {
      if (instance == null) {
        instance = new DownloadManagerImpl(context);
      }
    }
    return instance;
  }


  @Override
  public void download(Download download) {

  }

  @Override
  public void pause(Download download) {

  }

  @Override
  public void resume(Download download) {

  }

  @Override
  public void remove(Download download) {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Download getDownloadById(String id) {
    return null;
  }
}
