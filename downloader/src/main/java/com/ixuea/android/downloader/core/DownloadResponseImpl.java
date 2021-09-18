package com.ixuea.android.downloader.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.db.DownloadDBController;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.domain.DownloadThreadInfo;
import com.ixuea.android.downloader.exception.DownloadException;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */

public class DownloadResponseImpl implements DownloadResponse {

    private static final String TAG = "DownloadResponseImpl";
    private final Handler handler;
    private final DownloadDBController downloadDBController;
    private final DownloadManager downloadManager;

    public DownloadResponseImpl(DownloadManager downloadManager,DownloadDBController downloadDBController) {
        this.downloadManager = downloadManager;
        this.downloadDBController = downloadDBController;

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
                switch (downloadInfo.getStatus()) {
                    case DownloadInfo.STATUS_DOWNLOADING:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener()
                                    .onDownloading(downloadInfo.getProgress(), downloadInfo.getSize());
                        }

                        break;
                    case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onStart();
                        }
                        break;
                    case DownloadInfo.STATUS_WAIT:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onWaited();
                        }
                        break;
                    case DownloadInfo.STATUS_PAUSED:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onPaused();
                        }
                        break;
                    case DownloadInfo.STATUS_COMPLETED:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onDownloadSuccess();
                        }
                        //TODO submit next downloadInfo task

                        break;
                    case DownloadInfo.STATUS_ERROR:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onDownloadFailed(downloadInfo.getException());
                        }
                        break;
                    case DownloadInfo.STATUS_REMOVED:
                        if (downloadInfo.getDownloadListener() != null) {
                            downloadInfo.getDownloadListener().onRemoved();
                        }
                        break;
                }
            }
        };


    }

    @Override
    public void onStatusChanged(DownloadInfo downloadInfo) {
        createOrUpdateDownloadInfo(downloadInfo);

        Message message = handler.obtainMessage(downloadInfo.getId().hashCode());
        message.obj = downloadInfo;
        message.sendToTarget();

        Log.d(TAG, "progress:" + downloadInfo.getProgress() + ",size:" + downloadInfo.getSize());
    }

    private void createOrUpdateDownloadInfo(DownloadInfo downloadInfo) {
        if (downloadInfo.getStatus() != DownloadInfo.STATUS_REMOVED) {
            downloadDBController.createOrUpdate(downloadInfo);
            if (downloadInfo.getDownloadThreadInfos() != null) {
                for (DownloadThreadInfo threadInfo : downloadInfo.getDownloadThreadInfos()) {
                    downloadDBController.createOrUpdate(threadInfo);
                }
            }
        }
    }

    @Override
    public void handleException(DownloadInfo downloadInfo, DownloadException exception) {
        downloadInfo.setStatus(DownloadInfo.STATUS_ERROR);
        downloadInfo.setException(exception);

        createOrUpdateDownloadInfo(downloadInfo);

        Message message = handler.obtainMessage(downloadInfo.getId().hashCode());
        message.obj = downloadInfo;
        message.sendToTarget();

        Log.e(TAG, "handleException:" + exception.getLocalizedMessage());

        //下载下一个文件
        downloadManager.onDownloadFailed(downloadInfo);
    }
}
