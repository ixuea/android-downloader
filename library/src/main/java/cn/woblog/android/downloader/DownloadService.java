package cn.woblog.android.downloader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by renpingqing on 14/01/2017.
 */

public class DownloadService extends Service {

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
