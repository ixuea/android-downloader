package cn.woblog.android.downloader.simple.activity;

import static cn.woblog.android.downloader.DownloadService.downloadManager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.woblog.android.downloader.callback.DownloadListener;
import cn.woblog.android.downloader.domain.DownloadInfo;
import cn.woblog.android.downloader.domain.DownloadInfo.Builder;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.util.FileUtil;
import java.io.File;

public class SimpleActivity extends AppCompatActivity {

  public static final String DEFAULT_URL = "http://m.shouji.360tpcdn.com/170223/2e08d26f5e838b54470deec30c1565d2/com.facebook.katana_50465869.apk";


  private TextView tv_download_info;
  private Button bt_download_button;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple);

    initView();
    initData();
  }

  private void initData() {
    bt_download_button.setOnClickListener(new OnClickListener() {


      @Override
      public void onClick(View v) {
        downloadFile();
      }


    });
  }

  private void downloadFile() {
    File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "d");
    if (!d.exists()) {
      d.mkdirs();
    }
    String path = d.getAbsolutePath().concat("/").concat("a.apk");
    final DownloadInfo downloadInfo = new Builder().setUrl(DEFAULT_URL)
        .setPath(path)
        .build();
    downloadInfo
        .setDownloadListener(new DownloadListener() {

          @Override
          public void onStart() {
            tv_download_info.setText("Prepare downloading");
          }

          @Override
          public void onWaited() {
            tv_download_info.setText("Waiting");
          }

          @Override
          public void onPaused() {
            tv_download_info.setText("Paused");
          }

          @Override
          public void onDownloading() {
            tv_download_info
                .setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
                    .formatFileSize(downloadInfo.getSize()));
          }

          @Override
          public void onRemoved() {
          }

          @Override
          public void onDownloadSuccess() {
            tv_download_info.setText("Download success");
          }

          @Override
          public void onDownloadFailed() {
            tv_download_info.setText("Download fail");
          }
        });
    downloadManager.download(downloadInfo);
  }

  private void initView() {
    tv_download_info = (TextView) findViewById(R.id.tv_download_info);
    bt_download_button = (Button) findViewById(R.id.bt_download_button);


  }

//  class ViewHolder extends RecyclerView.ViewHolder {
//
//
//    private DownloadInfo downloadInfo;
//
//    public ViewHolder(View view) {
//      super(view);
//
//
//    }

//    @SuppressWarnings("unchecked")
//    public void bindData(final MyDownloadInfo data, int position, final Context context) {
//      Glide.with(context).load(data.getIcon()).into(iv_icon);
//      tv_name.setText(data.getName());
//
//      downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
//      if (downloadInfo != null) {
//        downloadInfo
//            .setDownloadListener(new MyDownloadListener(new SoftReference(DownloadListAdapter.ViewHolder.this)) {
//
//              @Override
//              public void onRefresh() {
//                if (getUserTag() != null && getUserTag().get() != null) {
//                  DownloadListAdapter.ViewHolder viewHolder = (DownloadListAdapter.ViewHolder) getUserTag().get();
//                  viewHolder.refresh();
//                }
//              }
//            });
//
//      }
//      refresh();
//
//      bt_action.setOnClickListener(new OnClickListener() {
//        @Override
//        public void onClick(View v) {
////          downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
//          if (downloadInfo != null) {
//
//            switch (downloadInfo.getStatus()) {
//              case DownloadInfo.STATUS_NONE:
//              case DownloadInfo.STATUS_PAUSED:
//              case DownloadInfo.STATUS_ERROR:
//
//                //resume downloadInfo
//                downloadManager.resume(downloadInfo);
//                break;
//
//              case DownloadInfo.STATUS_DOWNLOADING:
//              case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
//              case STATUS_WAIT:
//                //pause downloadInfo
//                downloadManager.pause(downloadInfo);
//                break;
//              case DownloadInfo.STATUS_COMPLETED:
//                downloadManager.remove(downloadInfo);
//                break;
//            }
//          } else {
//            //create downloadInfo
////            String path = context.getFilesDir().getAbsolutePath().concat("/")
////                .concat(data.getName());
//            File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "d");
//            if (!d.exists()) {
//              d.mkdirs();
//            }
//            String path = d.getAbsolutePath().concat("/").concat(data.getName());
//            downloadInfo = new Builder().setUrl(data.getUrl())
//                .setPath(path)
//                .build();
//            downloadInfo
//                .setDownloadListener(new MyDownloadListener(new SoftReference(DownloadListAdapter.ViewHolder.this)) {
//
//                  @Override
//                  public void onRefresh() {
//                    if (getUserTag() != null && getUserTag().get() != null) {
//                      DownloadListAdapter.ViewHolder viewHolder = (DownloadListAdapter.ViewHolder) getUserTag().get();
//                      viewHolder.refresh();
//                    }
//                  }
//                });
//            downloadManager.download(downloadInfo);
//          }
//        }
//      });
//
//    }
//
//    private void refresh() {
//      if (downloadInfo == null) {
//        tv_size.setText("");
//        pb.setProgress(0);
//        bt_action.setText("Download");
//        tv_status.setText("not downloadInfo");
//      } else {
//        switch (downloadInfo.getStatus()) {
//          case DownloadInfo.STATUS_NONE:
//            bt_action.setText("Download");
//            tv_status.setText("not downloadInfo");
//            break;
//          case DownloadInfo.STATUS_PAUSED:
//          case DownloadInfo.STATUS_ERROR:
//            bt_action.setText("Continue");
//            tv_status.setText("paused");
//            try {
//              pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//            tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
//                .formatFileSize(downloadInfo.getSize()));
//            break;
//
//          case DownloadInfo.STATUS_DOWNLOADING:
//          case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
//            bt_action.setText("Pause");
//            try {
//              pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//            tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
//                .formatFileSize(downloadInfo.getSize()));
//            tv_status.setText("downloading");
//            break;
//          case STATUS_COMPLETED:
//            bt_action.setText("Delete");
//            try {
//              pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//            tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
//                .formatFileSize(downloadInfo.getSize()));
//            tv_status.setText("success");
//            break;
//          case STATUS_REMOVED:
//            tv_size.setText("");
//            pb.setProgress(0);
//            bt_action.setText("Download");
//            tv_status.setText("not downloadInfo");
//          case STATUS_WAIT:
//            tv_size.setText("");
//            pb.setProgress(0);
//            bt_action.setText("Pause");
//            tv_status.setText("Waiting");
//            break;
//        }
//
//      }
//    }
//  }
}
