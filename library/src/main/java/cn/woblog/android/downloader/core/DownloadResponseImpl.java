package cn.woblog.android.downloader.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.domain.DownloadInfo;

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
        DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
        switch (downloadInfo.getStatus()) {
          case DownloadInfo.STATUS_DOWNLOADING:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onDownloading();
            }

            break;
          case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onStart();
            }
            break;
          case DownloadInfo.STATUS_WAIT:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onWaited();
            }
            break;
          case DownloadInfo.STATUS_PAUSED:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onPaused();
            }
            break;
          case DownloadInfo.STATUS_COMPLETED:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onDownloadSuccess();
            }
            //TODO submit next downloadInfo task

            break;
          case DownloadInfo.STATUS_ERROR:
            if (downloadInfo.getDownloadListener() != null) {
              downloadInfo.getDownloadListener().onDownloadFailed();
            }
            break;
        }
      }
    };
  }

  @Override
  public void onStatusChanged(DownloadInfo downloadInfo) {
    Message message = handler.obtainMessage(downloadInfo.getKey());
    message.obj = downloadInfo;
    message.sendToTarget();

    Log.d(TAG, "progress:" + downloadInfo.getProgress() + ",size:" + downloadInfo.getSize());
  }

  @Override
  public void handleException(DownloadException exception) {

  }
}
