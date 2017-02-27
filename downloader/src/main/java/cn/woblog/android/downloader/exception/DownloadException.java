package cn.woblog.android.downloader.exception;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadException extends RuntimeException {

  /**
   *
   */
  public static final int EXCEPTION_URL_NULL = 0;
  /**
   *
   */
  public static final int EXCEPTION_PATH_NULL = 1;
  /**
   *
   */
  public static final int EXCEPTION_URL_ERROR = 2;
  /**
   *
   */
  public static final int EXCEPTION_SERVER_ERROR = 3;
  /**
   *
   */
  public static final int EXCEPTION_PROTOCOL = 4;
  /**
   *
   */
  public static final int EXCEPTION_IO_EXCEPTION = 5;
  /**
   *
   */
  public static final int EXCEPTION_FILE_SIZE_ZERO = 6;
  /**
   *
   */
  public static final int EXCEPTION_PAUSE = 7;
  /**
   *
   */
  public static final int EXCEPTION_SERVER_SUPPORT_CODE = 8;

  /**
   *
   */
  public static final int EXCEPTION_OTHER = 9;

  private int code;

  public DownloadException(@ExceptionType int code) {
    this.code = code;
  }

  public DownloadException(@ExceptionType int code, String message) {
    super(message);
    this.code = code;
  }

  public DownloadException(@ExceptionType int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public DownloadException(@ExceptionType int code, Throwable cause) {
    super(cause);
    this.code = code;
  }


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Download exception type.
   */
  @IntDef({EXCEPTION_URL_NULL, EXCEPTION_PATH_NULL, EXCEPTION_URL_ERROR, EXCEPTION_SERVER_ERROR,
      EXCEPTION_PROTOCOL, EXCEPTION_IO_EXCEPTION, EXCEPTION_FILE_SIZE_ZERO, EXCEPTION_PAUSE,
      EXCEPTION_SERVER_SUPPORT_CODE, EXCEPTION_OTHER})
  @Retention(RetentionPolicy.SOURCE)
  public @interface ExceptionType {

  }

}
