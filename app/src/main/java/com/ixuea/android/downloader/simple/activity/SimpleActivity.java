package com.ixuea.android.downloader.simple.activity;

import static com.ixuea.android.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static com.ixuea.android.downloader.domain.DownloadInfo.STATUS_REMOVED;
import static com.ixuea.android.downloader.domain.DownloadInfo.STATUS_WAIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ixuea.android.common.activity.BaseActivity;
import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadListener;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.exception.DownloadException;
import com.ixuea.android.downloader.simple.R;
import com.ixuea.android.downloader.simple.util.FileUtil;

import java.io.File;


/**
 * How to download a file sample.
 */
public class SimpleActivity extends BaseActivity implements View.OnClickListener {

    public static final String DEFAULT_URL = "https://3b8637d9f6c334dab555e2afbdc16687.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/49F7A4E3B47E5828D02B2A10C580DB65.apk";


    private TextView tv_download_info;
    private Button bt_download_button;
    private DownloadManager downloadManager;
    private DownloadInfo downloadInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);


    }

    @Override
    public void initListener() {
        bt_download_button.setOnClickListener(this);
    }

    @Override
    public void initData() {
        downloadManager = DownloadService.getDownloadManager(getApplicationContext());

        downloadInfo = downloadManager.getDownloadById(DEFAULT_URL);

        if (downloadInfo != null) {
            setDownloadListener();
        }


        refresh();
    }

    private void refresh() {
        if (downloadInfo == null) {
            bt_download_button.setText("Download");
            tv_download_info.setText("");
            return;
        }
        switch (downloadInfo.getStatus()) {
            case DownloadInfo.STATUS_NONE:
                bt_download_button.setText("Download");
                tv_download_info.setText("");
                break;
            case DownloadInfo.STATUS_PAUSED:
                bt_download_button.setText("Continue");
                tv_download_info.setText("Paused");
                break;
            case DownloadInfo.STATUS_ERROR:
                bt_download_button.setText("Continue");

                String errorMessage="";
                if (downloadInfo.getException() != null) {
                    errorMessage=downloadInfo.getException().getLocalizedMessage();
                }
                tv_download_info.setText("Download fail:"+errorMessage);
                break;

            case DownloadInfo.STATUS_DOWNLOADING:
            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                tv_download_info
                        .setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                bt_download_button.setText("Pause");
                break;
            case STATUS_COMPLETED:
                bt_download_button.setText("Delete");
                tv_download_info.setText("Download success");
                break;
            case STATUS_REMOVED:
                bt_download_button.setText("Download");
                tv_download_info.setText("");
                downloadInfo = null;
            case STATUS_WAIT:
                tv_download_info.setText("Waiting");
                bt_download_button.setText("Pause");
                break;
        }
    }


    private void createDownload() {
        File d = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "d");
        if (!d.exists()) {
            d.mkdirs();
        }
        String path = d.getAbsolutePath().concat("/").concat("a.apk");
        downloadInfo = new DownloadInfo.Builder().setUrl(DEFAULT_URL)
                .setPath(path)
                .build();
        setDownloadListener();
        downloadManager.download(downloadInfo);
    }

    private void setDownloadListener() {
        downloadInfo.setDownloadListener(new DownloadListener() {

            @Override
            public void onStart() {
                tv_download_info.setText("Prepare downloading");
            }

            @Override
            public void onWaited() {
                tv_download_info.setText("Waiting");
                bt_download_button.setText("Pause");
            }

            @Override
            public void onPaused() {
                bt_download_button.setText("Continue");
                tv_download_info.setText("Paused");
            }

            @Override
            public void onDownloading(long progress, long size) {
                tv_download_info
                        .setText(FileUtil.formatFileSize(progress) + "/" + FileUtil
                                .formatFileSize(size));
                bt_download_button.setText("Pause");
            }

            @Override
            public void onRemoved() {
                bt_download_button.setText("Download");
                tv_download_info.setText("");
                downloadInfo = null;
            }

            @Override
            public void onDownloadSuccess() {
                bt_download_button.setText("Delete");
                tv_download_info.setText("Download success");
            }

            @Override
            public void onDownloadFailed(DownloadException e) {
                bt_download_button.setText("Continue");
                tv_download_info.setText("Download fail:" + e.getMessage());
            }
        });
    }

    @Override
    public void initView() {
        tv_download_info = findViewById(R.id.tv_download_info);
        bt_download_button = findViewById(R.id.bt_download_button);

    }

    @Override
    public void onClick(View v) {
        if (downloadInfo == null) {
            createDownload();
        } else {
            switch (downloadInfo.getStatus()) {
                case DownloadInfo.STATUS_NONE:
                case DownloadInfo.STATUS_PAUSED:
                case DownloadInfo.STATUS_ERROR:

                    //resume downloadInfo
                    downloadManager.resume(downloadInfo);
                    break;

                case DownloadInfo.STATUS_DOWNLOADING:
                case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                case STATUS_WAIT:
                    //pause downloadInfo
                    downloadManager.pause(downloadInfo);
                    break;
                case STATUS_COMPLETED:
                    downloadManager.remove(downloadInfo);
                    break;
            }
        }

    }


}
