package cn.woblog.android.downloader.core;

import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.DownloadManagerImpl.Config;
import cn.woblog.android.downloader.core.task.DownloadTask;
import cn.woblog.android.downloader.core.task.GetFileInfoTask;
import cn.woblog.android.downloader.core.task.GetFileInfoTask.OnGetFileInfoListener;
import cn.woblog.android.downloader.core.thread.DownloadThread;
import cn.woblog.android.downloader.core.thread.DownloadThread.DownloadProgressListener;
import cn.woblog.android.downloader.domain.Download;
import cn.woblog.android.downloader.domain.ThreadInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by renpingqing on 17/1/22.
 */

public class DownloadTaskImpl implements DownloadTask, OnGetFileInfoListener,
    DownloadProgressListener {

  private final ExecutorService executorService;
  private final DownloadResponse downloadResponse;
  private final Download download;
  private final Config config;
  private final List<DownloadThread> downloadThreads;
  private long lastRefreshTime = System.currentTimeMillis();
  private long progress;
  private volatile AtomicBoolean isComputerDownload = new AtomicBoolean(false);
//  private volatile boolean isComputerDownload;

  public DownloadTaskImpl(ExecutorService executorService, DownloadResponse downloadResponse,
      Download download, Config config) {
    this.executorService = executorService;
    this.downloadResponse = downloadResponse;
    this.download = download;
    this.config = config;
    this.downloadThreads = new ArrayList<>();

  }

  @Override
  public void start() {
    if (download.getSize() <= 0) {
      //get download info size
      getFileInfo();
    } else {
      List<ThreadInfo> threadInfos = download.getThreadInfos();
      for (ThreadInfo threadInfo : threadInfos
          ) {
        DownloadThread downloadThread = new DownloadThread(threadInfo, downloadResponse, config,
            download, this);
        executorService.submit(downloadThread);
        downloadThreads.add(downloadThread);
      }

      download.setStatus(Download.STATUS_DOWNLOADING);
      downloadResponse.onStatusChanged(download);
    }
  }

  private void getFileInfo() {
    GetFileInfoTask getFileInfoTask = new GetFileInfoTask(downloadResponse, download, this);
    executorService.submit(getFileInfoTask);
  }

  @Override
  public void onSuccess(long size, boolean isSupportRanges) {
    download.setSupportRanges(isSupportRanges);
    download.setSize(size);

    List<ThreadInfo> threadInfos = new ArrayList<>();
    if (isSupportRanges) {
      long length = download.getSize();
      final int threads = config.getEachDownloadThread();
      final long average = length / threads;
      for (int i = 0; i < threads; i++) {
        long start = average * i;
        long end;
        if (i == threads - 1) {
          end = length;
        } else {
          end = start + average - 1;
        }
        ThreadInfo threadInfo = new ThreadInfo(i, download.getKey(), download.getUrl(), start,
            end);
        threadInfos.add(threadInfo);

        DownloadThread downloadThread = new DownloadThread(threadInfo, downloadResponse, config,
            download, this);
        executorService.submit(downloadThread);
        downloadThreads.add(downloadThread);
      }
    } else {
      ThreadInfo threadInfo = new ThreadInfo(0, download.getKey(), download.getUrl(), 0,
          download.getSize());
      threadInfos.add(threadInfo);

      DownloadThread downloadThread = new DownloadThread(threadInfo, downloadResponse, config,
          download, this);
      executorService.submit(downloadThread);
      downloadThreads.add(downloadThread);
    }
    download.setThreadInfos(threadInfos);
    download.setStatus(Download.STATUS_DOWNLOADING);
    downloadResponse.onStatusChanged(download);

  }

  @Override
  public void onFailed(DownloadException exception) {

  }

  @Override
  public void onProgress() {
    if (!isComputerDownload.get()) {
      synchronized (this) {
        if (!isComputerDownload.get()) {
          isComputerDownload.set(true);
          long currentTimeMillis = System.currentTimeMillis();
          if ((currentTimeMillis - lastRefreshTime) > 1000) {
            computerDownloadProgress();
            downloadResponse.onStatusChanged(download);
            lastRefreshTime = currentTimeMillis;
          }
          isComputerDownload.set(false);
        }
      }
    }

  }

  @Override
  public void onDownloadSuccess() {
    computerDownloadProgress();
    if (download.getProgress() == download.getSize()) {
      download.setStatus(Download.STATUS_COMPLETED);
      downloadResponse.onStatusChanged(download);
    }
  }

  private void computerDownloadProgress() {
    progress = 0;
    List<ThreadInfo> threadInfos = download.getThreadInfos();
    for (ThreadInfo info :
        threadInfos) {
      progress += info.getProgress();
    }
    download.setProgress(progress);

  }
}
