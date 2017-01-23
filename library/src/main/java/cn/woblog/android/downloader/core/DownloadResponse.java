package cn.woblog.android.downloader.core;

import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.domain.DownloadInfo;

/**
 * Created by renpingqing on 17/1/22.
 */

public interface DownloadResponse {

  void onStatusChanged(DownloadInfo downloadInfo);

  void handleException(DownloadException exception);
}
