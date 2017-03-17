package cn.woblog.android.downloader.simple.activity;

import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_REMOVED;
import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_WAIT;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.woblog.android.common.activity.BaseActivity;
import cn.woblog.android.downloader.DownloadService;
import cn.woblog.android.downloader.callback.DownloadManager;
import cn.woblog.android.downloader.domain.DownloadInfo;
import cn.woblog.android.downloader.domain.DownloadInfo.Builder;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.callback.MyDownloadListener;
import cn.woblog.android.downloader.simple.domain.MyBusinessInfo;
import cn.woblog.android.downloader.simple.util.FileUtil;
import com.bumptech.glide.Glide;
import java.io.File;
import java.lang.ref.SoftReference;

/**
 * Created by ren on 2015/4/27 0027.
 */
public class DownloadDetailActivity extends BaseActivity {

  public static final String DATA = "DATA";
  private DownloadManager downloadManager;
  private DownloadInfo downloadInfo;

  private ImageView iv_icon;
  private TextView tv_size;
  private TextView tv_status;
  private ProgressBar pb;
  private TextView tv_name;
  private Button bt_action;
  private MyBusinessInfo data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download_detail);
  }


  @Override
  protected void initView() {
    super.initView();
    iv_icon = (ImageView) findViewById(R.id.iv_icon);
    tv_size = (TextView) findViewById(R.id.tv_size);
    tv_status = (TextView) findViewById(R.id.tv_status);
    pb = (ProgressBar) findViewById(R.id.pb);
    tv_name = (TextView) findViewById(R.id.tv_name);
    bt_action = (Button) findViewById(R.id.bt_action);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void initData() {
    super.initData();
    data = (MyBusinessInfo) getIntent().getSerializableExtra(DATA);
    Glide.with(this).load(data.getIcon()).into(iv_icon);

    downloadManager = DownloadService.getDownloadManager(getApplicationContext());

    downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());

    if (downloadInfo != null) {
      downloadInfo
          .setDownloadListener(new MyDownloadListener(new SoftReference(null)) {

            @Override
            public void onRefresh() {
              refresh();
            }
          });
    }

    refresh();

    bt_action.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (downloadInfo != null) {

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
            case DownloadInfo.STATUS_COMPLETED:
              downloadManager.remove(downloadInfo);
              break;
          }
        } else {
//            Create new download task
          File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "d");
          if (!d.exists()) {
            d.mkdirs();
          }
          String path = d.getAbsolutePath().concat("/").concat(data.getName());
          downloadInfo = new Builder().setUrl(data.getUrl())
              .setPath(path)
              .build();
          downloadInfo
              .setDownloadListener(new MyDownloadListener(new SoftReference(null)) {

                @Override
                public void onRefresh() {
                  refresh();
                }
              });
          downloadManager.download(downloadInfo);
        }
      }
    });
  }

  private void refresh() {
    if (downloadInfo == null) {
      tv_size.setText("");
      pb.setProgress(0);
      bt_action.setText("Download");
      tv_status.setText("not downloadInfo");
    } else {
      switch (downloadInfo.getStatus()) {
        case DownloadInfo.STATUS_NONE:
          bt_action.setText("Download");
          tv_status.setText("not downloadInfo");
          break;
        case DownloadInfo.STATUS_PAUSED:
        case DownloadInfo.STATUS_ERROR:
          bt_action.setText("Continue");
          tv_status.setText("paused");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          break;

        case DownloadInfo.STATUS_DOWNLOADING:
        case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
          bt_action.setText("Pause");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          tv_status.setText("downloading");
          break;
        case STATUS_COMPLETED:
          bt_action.setText("Delete");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          tv_status.setText("success");
          break;
        case STATUS_REMOVED:
          tv_size.setText("");
          pb.setProgress(0);
          bt_action.setText("Download");
          tv_status.setText("not downloadInfo");
        case STATUS_WAIT:
          tv_size.setText("");
          pb.setProgress(0);
          bt_action.setText("Pause");
          tv_status.setText("Waiting");
          break;
      }

    }
  }

}
