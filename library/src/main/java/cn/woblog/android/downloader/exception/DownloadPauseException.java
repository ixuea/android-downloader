package cn.woblog.android.downloader.exception;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadPauseException extends DownloadException {


  public DownloadPauseException(@EXCEPTION_TYPE int code) {
    super(code);
  }

  public DownloadPauseException(@EXCEPTION_TYPE int code, String message) {
    super(code, message);
  }

  public DownloadPauseException(@EXCEPTION_TYPE int code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public DownloadPauseException(@EXCEPTION_TYPE int code, Throwable cause) {
    super(code, cause);
  }
}
