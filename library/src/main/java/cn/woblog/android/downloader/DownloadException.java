package cn.woblog.android.downloader;

import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadException extends RuntimeException {

  public DownloadException() {
  }

  public DownloadException(String message) {
    super(message);
  }

  public DownloadException(String message, Throwable cause) {
    super(message, cause);
  }

  public DownloadException(Throwable cause) {
    super(cause);
  }

  @RequiresApi(api = VERSION_CODES.N)
  public DownloadException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }


}
