package com.ixuea.android.downloader.exception;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */

public class DownloadPauseException extends DownloadException {


    public DownloadPauseException(@ExceptionType int code) {
        super(code);
    }

    public DownloadPauseException(@ExceptionType int code, String message) {
        super(code, message);
    }

    public DownloadPauseException(@ExceptionType int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public DownloadPauseException(@ExceptionType int code, Throwable cause) {
        super(code, cause);
    }
}
