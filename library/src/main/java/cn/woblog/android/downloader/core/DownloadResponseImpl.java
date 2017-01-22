package cn.woblog.android.downloader.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.domain.Download;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadResponseImpl implements DownloadResponse {

  private static final String TAG = "DownloadResponseImpl";
  private final Handler handler;

  public DownloadResponseImpl() {
    handler = new Handler(Looper.getMainLooper()) {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Download download = (Download) msg.obj;
        switch (download.getStatus()) {
          case Download.STATUS_DOWNLOADING:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onDownloading();
            }

            break;
          case Download.STATUS_PREPARE_DOWNLOAD:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onStart();
            }
            break;
          case Download.STATUS_WAIT:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onWaited();
            }
            break;
          case Download.STATUS_PAUSED:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onPaused();
            }
            break;
          case Download.STATUS_COMPLETED:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onDownloadSuccess();
            }
            //TODO submit next download task

            break;
          case Download.STATUS_ERROR:
            if (download.getDownloadListener() != null) {
              download.getDownloadListener().onDownloadFailed();
            }
            break;
        }
      }
    };
  }

  @Override
  public void onStatusChanged(Download download) {
    Message message = handler.obtainMessage(download.getKey());
    message.obj = download;
    message.sendToTarget();

    Log.d(TAG, "progress:" + download.getProgress() + ",size:" + download.getSize());
  }

  @Override
  public void handleException(DownloadException exception) {

  }
}
