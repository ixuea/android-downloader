package cn.woblog.android.downloader;

import android.content.Context;
import cn.woblog.android.downloader.callback.DownloadManager;
import cn.woblog.android.downloader.core.DownloadResponse;
import cn.woblog.android.downloader.core.DownloadResponseImpl;
import cn.woblog.android.downloader.core.DownloadTaskImpl;
import cn.woblog.android.downloader.core.task.DownloadTask;
import cn.woblog.android.downloader.domain.Download;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by renpingqing on 14/01/2017.
 */

public final class DownloadManagerImpl implements DownloadManager {

  private static DownloadManagerImpl instance;
  private final ExecutorService executorService;
  private final ConcurrentHashMap<Integer, DownloadTask> cacheDownloadTask;
  private final List<Download> watingDownloads;
  private final Context context;

  private final DownloadResponse downloadResponse;
  private final Config config;

  private DownloadManagerImpl(Context context, Config config) {
    this.context = context;
    if (config == null) {
      this.config = new Config();
    } else {
      this.config = config;
    }
    cacheDownloadTask = new ConcurrentHashMap<>();
    watingDownloads = new LinkedList<>();

    executorService = Executors.newFixedThreadPool(this.config.getDownloadThread());

    downloadResponse = new DownloadResponseImpl();
  }

  public static DownloadManager getInstance(Context context) {
    return getInstance(context, null);
  }

  public static DownloadManager getInstance(Context context, Config config) {
    synchronized (DownloadManagerImpl.class) {
      if (instance == null) {
        instance = new DownloadManagerImpl(context, config);
      }
    }
    return instance;
  }


  @Override
  public void download(Download download) {
    if (cacheDownloadTask.size() >= config.getDownloadThread()) {
      watingDownloads.add(download);
      download.setStatus(Download.STATUS_WAIT);
      downloadResponse.onStatusChanged(download);
    } else {
      DownloadTaskImpl downloadTask = new DownloadTaskImpl(executorService, downloadResponse,
          download, config);
      cacheDownloadTask.put(download.getKey(), downloadTask);
      download.setStatus(Download.STATUS_PREPARE_DOWNLOAD);
      downloadResponse.onStatusChanged(download);
      downloadTask.start();
    }

  }

  @Override
  public void pause(Download download) {

  }

  @Override
  public void resume(Download download) {

  }

  @Override
  public void remove(Download download) {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Download getDownloadById(String id) {
    return null;
  }

  public class Config {

    private int connectTimeout = 5000;
    private int readTimeout = 5000;

    private int downloadThread = 5;

    private int eachDownloadThread = 2;

    private String method = "GET";

    public int getConnectTimeout() {
      return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
      this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
      return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
    }

    public int getDownloadThread() {
      return downloadThread;
    }

    public void setDownloadThread(int downloadThread) {
      this.downloadThread = downloadThread;
    }

    public int getEachDownloadThread() {
      return eachDownloadThread;
    }

    public void setEachDownloadThread(int eachDownloadThread) {
      this.eachDownloadThread = eachDownloadThread;
    }

    public String getMethod() {
      return method;
    }

    public void setMethod(String method) {
      this.method = method;
    }
  }

}
