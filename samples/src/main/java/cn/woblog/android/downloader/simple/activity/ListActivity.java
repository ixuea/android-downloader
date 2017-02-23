package cn.woblog.android.downloader.simple.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.adapter.DownloadListAdapter;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfo;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

  private RecyclerView rv;
  private DownloadListAdapter downloadListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    initView();
    initData();
  }

  private void initData() {
    downloadListAdapter = new DownloadListAdapter(this);

    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setAdapter(downloadListAdapter);

    downloadListAdapter.setData(getDownloadListData());
  }

  private List<MyDownloadInfo> getDownloadListData() {
    ArrayList<MyDownloadInfo> myDownloadInfos = new ArrayList<>();
    myDownloadInfos.add(new MyDownloadInfo("QQ",
        "http://img.wdjimg.com/mms/icon/v1/4/c6/e3ff9923c44e59344e8b9aa75e948c64_256_256.png",
        "http://wdj-qn-apk.wdjcdn.com/e/b8/520c1a2208bf7724b96f538247233b8e.apk"));
    myDownloadInfos.add(new MyDownloadInfo("微信",
        "http://img.wdjimg.com/mms/icon/v1/7/ed/15891412e00a12fdec0bbe290b42ced7_256_256.png",
        "http://wdj-uc1-apk.wdjcdn.com/1/a3/8ee2c3f8a6a4a20116eed72e7645aa31.apk"));
    myDownloadInfos.add(new MyDownloadInfo("360手机卫士",
        "http://img.wdjimg.com/mms/icon/v1/d/29/dc596253e9e80f28ddc84fe6e52b929d_256_256.png",
        "http://wdj-qn-apk.wdjcdn.com/4/0b/ce61a5f6093fe81502fc0092dd6700b4.apk"));
    myDownloadInfos.add(new MyDownloadInfo("陌陌",
        "http://img.wdjimg.com/mms/icon/v1/a/6e/03d4e21876706e6a175ff899afd316ea_256_256.png",
        "http://wdj-qn-apk.wdjcdn.com/b/0a/369eec172611626efff4e834fedce0ab.apk"));
    myDownloadInfos.add(new MyDownloadInfo("美颜相机",
        "http://img.wdjimg.com/mms/icon/v1/7/7b/eb6b7905241f22b54077cbd632fe87b7_256_256.png",
        "http://wdj-qn-apk.wdjcdn.com/a/e9/618d265197a43dab6277c41ec5f72e9a.apk"));
    myDownloadInfos.add(new MyDownloadInfo("Chrome",
        "http://img.wdjimg.com/mms/icon/v1/d/fd/914f576f9fa3e9e7aab08ad0a003cfdd_256_256.png",
        "http://wdj-qn-apk.wdjcdn.com/6/0d/6e93a829b97d671ee56190aec78400d6.apk"));
    myDownloadInfos.add(new MyDownloadInfo("Facebook",
        "http://img.wdjimg.com/mms/icon/v1/b/d0/ac5196fbf245580eee113296dff14d0b_256_256.png",
        "http://m.shouji.360tpcdn.com/170223/2e08d26f5e838b54470deec30c1565d2/com.facebook.katana_50465869.apk"));
    return myDownloadInfos;
  }

  private void initView() {
    rv = (RecyclerView) findViewById(R.id.rv);
  }
}
