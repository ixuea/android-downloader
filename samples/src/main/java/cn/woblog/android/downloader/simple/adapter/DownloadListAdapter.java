package cn.woblog.android.downloader.simple.adapter;

import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_REMOVED;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.woblog.android.downloader.DownloadService;
import cn.woblog.android.downloader.callback.DownloadManager;
import cn.woblog.android.downloader.domain.DownloadInfo;
import cn.woblog.android.downloader.domain.DownloadInfo.Builder;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.callback.MyDownloadListener;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfo;
import cn.woblog.android.downloader.simple.util.FileUtil;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpingqing on 17/1/19.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

  private static final String TAG = "DownloadListAdapter";
  private final Context context;
  private final DownloadManager downloadManager;
  private List<MyDownloadInfo> data = new ArrayList<>();

  public DownloadListAdapter(Context context) {
    this.context = context;
    downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(context).inflate(
        R.layout.item_download_info, parent, false));
  }

  @Override
  public void onBindViewHolder(DownloadListAdapter.ViewHolder holder, int position) {
    holder.bindData(getData(position), position, context);
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }

  private MyDownloadInfo getData(int position) {
    return data.get(position);
  }


  @Override
  public int getItemCount() {
    return data.size();
  }

  public void setData(List<MyDownloadInfo> data) {
    this.data.clear();
    this.data.addAll(data);
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView iv_icon;
    private final TextView tv_size;
    private final TextView tv_status;
    private final ProgressBar pb;
    private final TextView tv_name;
    private final Button bt_action;
    private DownloadInfo downloadInfo;

    public ViewHolder(View view) {
      super(view);

      iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
      tv_size = (TextView) view.findViewById(R.id.tv_size);
      tv_status = (TextView) view.findViewById(R.id.tv_status);
      pb = (ProgressBar) view.findViewById(R.id.pb);
      tv_name = (TextView) view.findViewById(R.id.tv_name);
      bt_action = (Button) view.findViewById(R.id.bt_action);
    }

    @SuppressWarnings("unchecked")
    public void bindData(final MyDownloadInfo data, int position, final Context context) {
//      Picasso.with(context).load(data.getIcon()).into(iv_icon);
      tv_name.setText(data.getName());

      downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
      if (downloadInfo != null) {
        downloadInfo
            .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder.this)) {

              @Override
              public void onRefresh() {
                if (getUserTag() != null && getUserTag().get() != null) {
                  ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                  viewHolder.refresh();
                }
              }
            });

      }
      refresh();

      bt_action.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
//          downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
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
                //pause downloadInfo
                downloadManager.pause(downloadInfo);
                break;
              case DownloadInfo.STATUS_COMPLETED:
                downloadManager.remove(downloadInfo);
                break;
            }
          } else {
            //create downloadInfo
//            String path = context.getFilesDir().getAbsolutePath().concat("/")
//                .concat(data.getName());
            File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "d");
            if (!d.exists()) {
              d.mkdirs();
            }
            String path = d.getAbsolutePath().concat("/").concat(data.getName());
            downloadInfo = new Builder().setUrl(data.getUrl())
                .setPath(path)
                .build();
            downloadInfo
                .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder.this)) {

                  @Override
                  public void onRefresh() {
                    if (getUserTag() != null && getUserTag().get() != null) {
                      ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                      viewHolder.refresh();
                    }
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
        bt_action.setText("download");
        tv_status.setText("not downloadInfo");
      } else {
        switch (downloadInfo.getStatus()) {
          case DownloadInfo.STATUS_NONE:
            bt_action.setText("downloadInfo");
            tv_status.setText("not downloadInfo");
            break;
          case DownloadInfo.STATUS_PAUSED:
          case DownloadInfo.STATUS_ERROR:
            bt_action.setText("continue");
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
            bt_action.setText("pause");
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
            bt_action.setText("delete");
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
            bt_action.setText("download");
            tv_status.setText("not downloadInfo");
            break;
        }

      }
    }
  }
}
