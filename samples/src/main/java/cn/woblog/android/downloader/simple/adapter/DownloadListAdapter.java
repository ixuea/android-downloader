package cn.woblog.android.downloader.simple.adapter;

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
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpingqing on 17/1/19.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

  private final Context context;
  private List<MyDownloadInfo> data = new ArrayList<>();

  public DownloadListAdapter(Context context) {
    this.context = context;
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

    public ViewHolder(View view) {
      super(view);

      iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
      tv_size = (TextView) view.findViewById(R.id.tv_size);
      tv_status = (TextView) view.findViewById(R.id.tv_status);
      pb = (ProgressBar) view.findViewById(R.id.pb);
      tv_name = (TextView) view.findViewById(R.id.tv_name);
      bt_action = (Button) view.findViewById(R.id.bt_action);
    }

    public void bindData(MyDownloadInfo data, int position, Context context) {
      Picasso.with(context).load(data.getIcon()).into(iv_icon);
      tv_name.setText(data.getName());
    }
  }
}
