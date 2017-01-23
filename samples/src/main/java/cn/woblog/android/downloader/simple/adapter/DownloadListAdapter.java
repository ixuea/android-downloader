package cn.woblog.android.downloader.simple.adapter;

import static cn.woblog.android.downloader.domain.Download.STATUS_COMPLETED;
import static cn.woblog.android.downloader.domain.Download.STATUS_REMOVED;

import android.content.Context;
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
import cn.woblog.android.downloader.domain.Download;
import cn.woblog.android.downloader.domain.Download.Builder;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.callback.MyDownloadListener;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfo;
import cn.woblog.android.downloader.simple.util.FileUtil;
import com.squareup.picasso.Picasso;
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
    private Download download;

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
      Picasso.with(context).load(data.getIcon()).into(iv_icon);
      tv_name.setText(data.getName());

      download = downloadManager.getDownloadById(data.getUrl());

      bt_action.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          download = downloadManager.getDownloadById(data.getUrl());
          if (download != null) {

            switch (download.getStatus()) {
              case Download.STATUS_NONE:
              case Download.STATUS_PAUSED:
              case Download.STATUS_ERROR:

                //resume download
                downloadManager.resume(download);
                break;

              case Download.STATUS_DOWNLOADING:
              case Download.STATUS_PREPARE_DOWNLOAD:
                //pause download
                downloadManager.pause(download);
                break;
              case Download.STATUS_COMPLETED:
                downloadManager.remove(download);
                break;
            }
          } else {
            //create download
            String path = context.getFilesDir().getAbsolutePath().concat("/")
                .concat(data.getName());
            download = new Builder().setUrl(data.getUrl())
                .setPath(path)
                .build();
            download
                .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder.this)) {

                  @Override
                  public void onRefresh() {
                    if (getUserTag() != null && getUserTag().get() != null) {
                      ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                      viewHolder.refresh();
                    }
                  }
                });
            downloadManager.download(download);
          }
        }
      });

    }

    private void refresh() {
      if (download == null) {
        bt_action.setText("download");
        tv_status.setText("not download");
      } else {
        switch (download.getStatus()) {
          case Download.STATUS_NONE:
            bt_action.setText("download");
            tv_status.setText("not download");
            break;
          case Download.STATUS_PAUSED:
          case Download.STATUS_ERROR:
            bt_action.setText("continue");
            tv_status.setText("paused");
            break;

          case Download.STATUS_DOWNLOADING:
          case Download.STATUS_PREPARE_DOWNLOAD:
            bt_action.setText("pause");
            try {
              pb.setProgress((int) (download.getProgress() * 100.0 / download.getSize()));
            } catch (Exception e) {
              e.printStackTrace();
            }
            tv_size.setText(FileUtil.formatFileSize(download.getProgress()) + "/" + FileUtil
                .formatFileSize(download.getSize()));
            tv_status.setText("downloading");
            break;
          case STATUS_COMPLETED:
            bt_action.setText("delete");
            try {
              pb.setProgress((int) (download.getProgress() * 100.0 / download.getSize()));
            } catch (Exception e) {
              e.printStackTrace();
            }
            tv_size.setText(FileUtil.formatFileSize(download.getProgress()) + "/" + FileUtil
                .formatFileSize(download.getSize()));
            tv_status.setText("success");
            break;
          case STATUS_REMOVED:
            tv_size.setText("");
            pb.setProgress(0);
            bt_action.setText("download");
            tv_status.setText("not download");
            break;
        }

      }
    }
  }
}
