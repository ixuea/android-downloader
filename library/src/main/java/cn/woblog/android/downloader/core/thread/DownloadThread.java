package cn.woblog.android.downloader.core.thread;


import android.os.Process;
import android.util.Log;
import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.DownloadManagerImpl.Config;
import cn.woblog.android.downloader.core.DownloadResponse;
import cn.woblog.android.downloader.domain.Download;
import cn.woblog.android.downloader.domain.ThreadInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadThread implements Runnable {

  public static final String TAG = "DownloadThread";

  private final ThreadInfo threadInfo;
  private final DownloadResponse downloadResponse;
  private final Config config;
  private final Download download;
  private final DownloadProgressListener downloadProgressListener;
  private long lastProgress;
  private InputStream inputStream;

  public DownloadThread(ThreadInfo threadInfo, DownloadResponse downloadResponse, Config config,
      Download download, DownloadProgressListener downloadProgressListener) {
    this.threadInfo = threadInfo;
    this.downloadResponse = downloadResponse;
    this.config = config;
    this.download = download;
    this.lastProgress = threadInfo.getProgress();
    this.downloadProgressListener = downloadProgressListener;
  }

  @Override
  public void run() {
    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
    checkIfPause();
    try {
      executeDownload();
    } catch (DownloadException e) {
      e.printStackTrace();
      downloadResponse.handleException(e);
    }
  }

  private void executeDownload() {
    HttpURLConnection httpConnection = null;
    try {
      final URL url = new URL(threadInfo.getUri());
      httpConnection = (HttpURLConnection) url.openConnection();
      httpConnection.setConnectTimeout(config.getConnectTimeout());
      httpConnection.setReadTimeout(config.getReadTimeout());
      httpConnection.setRequestMethod(config.getMethod());
      if (download.isSupportRanges()) {
        httpConnection.setRequestProperty("Range",
            "bytes=" + threadInfo.getStart() + "-" + threadInfo.getEnd());
      }
      final int responseCode = httpConnection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_PARTIAL
          || responseCode == HttpURLConnection.HTTP_OK) {
        inputStream = httpConnection.getInputStream();
        RandomAccessFile raf = new RandomAccessFile(download.getPath(), "rwd");
        raf.seek(lastProgress);
        final byte[] bf = new byte[1024 * 8];
        int len = -1;
        int offset = 0;
        while (true) {
          checkIfPause();
          len = inputStream.read(bf);
          if (len == -1) {
            break;
          }
          raf.write(bf, 0, len);
          offset += len;

//          synchronized (downloadProgressListener) {
          threadInfo.setProgress(lastProgress + offset);
            downloadProgressListener.onProgress();
//          }

          Log.d(TAG,
              "download thread " + threadInfo.getId() + " progress:" + threadInfo.getProgress()
                  + ",start:" + threadInfo.getStart() + ",end:" + threadInfo.getEnd());
        }

        //download success
        downloadProgressListener.onDownloadSuccess();
      } else {
        throw new DownloadException(DownloadException.EXCEPTION_SERVER_SUPPORT_CODE,
            "UnSupported response code:" + responseCode);
      }
      checkIfPause();
    } catch (ProtocolException e) {
      throw new DownloadException(DownloadException.EXCEPTION_PROTOCOL, "Protocol error", e);
    } catch (IOException e) {
      throw new DownloadException(DownloadException.EXCEPTION_IO_EXCEPTION, "IO error", e);
    } catch (Exception e) {
      throw new DownloadException(DownloadException.EXCEPTION_OTHER, "other error", e);
    } finally {
      if (httpConnection != null) {
        httpConnection.disconnect();
      }
    }
  }

  private void checkIfPause() {
    if (download.getStatus() == Download.STATUS_PAUSED) {
      throw new DownloadException(DownloadException.EXCEPTION_PAUSE);
    }
  }

  public interface DownloadProgressListener {

    void onProgress();

    void onDownloadSuccess();
  }


}
