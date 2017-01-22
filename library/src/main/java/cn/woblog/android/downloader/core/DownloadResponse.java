package cn.woblog.android.downloader.core;

import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.domain.Download;

/**
 * Created by renpingqing on 17/1/22.
 */

public interface DownloadResponse {

  void onStatusChanged(Download download);

  void handleException(DownloadException exception);
}
