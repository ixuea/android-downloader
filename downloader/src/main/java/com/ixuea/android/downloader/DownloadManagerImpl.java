package com.ixuea.android.downloader;

import android.content.Context;

import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.config.Config;
import com.ixuea.android.downloader.core.DownloadResponse;
import com.ixuea.android.downloader.core.DownloadResponseImpl;
import com.ixuea.android.downloader.core.DownloadTaskImpl;
import com.ixuea.android.downloader.core.DownloadTaskImpl.DownloadTaskListener;
import com.ixuea.android.downloader.core.task.DownloadTask;
import com.ixuea.android.downloader.db.DefaultDownloadDBController;
import com.ixuea.android.downloader.db.DownloadDBController;
import com.ixuea.android.downloader.domain.DownloadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 14/01/2017.
 */

public final class DownloadManagerImpl implements DownloadManager, DownloadTaskListener {

    private static final int MIN_EXECUTE_INTERVAL = 500;
    private static DownloadManagerImpl instance;
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, DownloadTask> cacheDownloadTask;
    private final List<DownloadInfo> downloadingCaches;
    private final Context context;

    private final DownloadResponse downloadResponse;
    private final DownloadDBController downloadDBController;
    private final Config config;
    private long lastExecuteTime;

    private DownloadManagerImpl(Context context, Config config) {
        this.context = context;
        if (config == null) {
            this.config = new Config();
        } else {
            this.config = config;
        }

        if (this.config.getDownloadDBController() == null) {
            downloadDBController = new DefaultDownloadDBController(context, this.config);
        } else {
            downloadDBController = this.config.getDownloadDBController();
        }

        if (downloadDBController.findAllDownloading() == null) {
            downloadingCaches = new ArrayList<>();
        } else {
            downloadingCaches = downloadDBController.findAllDownloading();
        }

        cacheDownloadTask = new ConcurrentHashMap<>();

        downloadDBController.pauseAllDownloading();

        executorService = Executors.newFixedThreadPool(this.config.getDownloadThread());

        downloadResponse = new DownloadResponseImpl(downloadDBController);
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
    public void download(DownloadInfo downloadInfo) {
        downloadingCaches.add(downloadInfo);
        prepareDownload(downloadInfo);
    }

    private void prepareDownload(DownloadInfo downloadInfo) {
        if (cacheDownloadTask.size() >= config.getDownloadThread()) {
            downloadInfo.setStatus(DownloadInfo.STATUS_WAIT);
            downloadResponse.onStatusChanged(downloadInfo);
        } else {
            DownloadTaskImpl downloadTask = new DownloadTaskImpl(executorService, downloadResponse,
                    downloadInfo, config, this);
            cacheDownloadTask.put(downloadInfo.getId(), downloadTask);
            downloadInfo.setStatus(DownloadInfo.STATUS_PREPARE_DOWNLOAD);
            downloadResponse.onStatusChanged(downloadInfo);
            downloadTask.start();
        }
    }

    @Override
    public void pause(DownloadInfo downloadInfo) {
        if (isExecute()) {
            pauseInner(downloadInfo);
        }
    }

    private void prepareDownloadNextTask() {
        for (DownloadInfo downloadInfo : downloadingCaches) {
            if (downloadInfo.getStatus() == DownloadInfo.STATUS_WAIT) {
                prepareDownload(downloadInfo);
                break;
            }
        }
    }

    @Override
    public void resume(DownloadInfo downloadInfo) {
        if (isExecute()) {
            prepareDownload(downloadInfo);
        }
    }

    @Override
    public void remove(DownloadInfo downloadInfo) {
        downloadInfo.setStatus(DownloadInfo.STATUS_REMOVED);
        cacheDownloadTask.remove(downloadInfo.getId());
        downloadingCaches.remove(downloadInfo);
        downloadDBController.delete(downloadInfo);
        downloadResponse.onStatusChanged(downloadInfo);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public DownloadInfo getDownloadById(String id) {
        DownloadInfo downloadInfo = null;
        for (DownloadInfo d : downloadingCaches) {
            if (d.getId().equals(id)) {
                downloadInfo = d;
                break;
            }
        }

        if (downloadInfo == null) {
            downloadInfo = downloadDBController.findDownloadedInfoById(id);
        }
        return downloadInfo;
    }

    @Override
    public List<DownloadInfo> findAllDownloading() {
        return downloadingCaches;
    }

    @Override
    public List<DownloadInfo> findAllDownloaded() {
        return downloadDBController.findAllDownloaded();
    }

    @Override
    public DownloadDBController getDownloadDBController() {
        return downloadDBController;
    }

    @Override
    public void resumeAll() {
        if (isExecute()) {
            for (DownloadInfo downloadInfo : downloadingCaches) {
                prepareDownload(downloadInfo);
            }
        }

    }

    @Override
    public void pauseAll() {
        if (isExecute()) {
            for (DownloadInfo downloadInfo : downloadingCaches) {
                pauseInner(downloadInfo);
            }
        }
    }

    private void pauseInner(DownloadInfo downloadInfo) {
        downloadInfo.setStatus(DownloadInfo.STATUS_PAUSED);
        cacheDownloadTask.remove(downloadInfo.getId());
        downloadResponse.onStatusChanged(downloadInfo);
        prepareDownloadNextTask();
    }

    @Override
    public void onDownloadSuccess(DownloadInfo downloadInfo) {
        cacheDownloadTask.remove(downloadInfo.getId());
        downloadingCaches.remove(downloadInfo);
        prepareDownloadNextTask();
    }

    public boolean isExecute() {
        if (System.currentTimeMillis() - lastExecuteTime > MIN_EXECUTE_INTERVAL) {
            lastExecuteTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }


}
