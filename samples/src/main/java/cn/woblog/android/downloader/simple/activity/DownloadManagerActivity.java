package cn.woblog.android.downloader.simple.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import cn.woblog.android.common.activity.BaseActivity;
import cn.woblog.android.downloader.simple.R;
import cn.woblog.android.downloader.simple.adapter.DownloadManagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Download manager page.
 */
public class DownloadManagerActivity extends BaseActivity {

  private TabLayout tl;
  private ViewPager vp;
  private DownloadManagerAdapter downloadManagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download_manager);
  }

  @Override
  protected void initView() {
    super.initView();
    tl = (TabLayout) findViewById(R.id.tl);
    vp = (ViewPager) findViewById(R.id.vp);
  }

  @Override
  protected void initData() {
    super.initData();

    downloadManagerAdapter = new DownloadManagerAdapter(
        getSupportFragmentManager(), getActivity());

    List<String> strings = new ArrayList<>();
    strings.add("Downloading");
    strings.add("Downloaded");

    downloadManagerAdapter.setData(strings);
    vp.setAdapter(downloadManagerAdapter);
    tl.setupWithViewPager(vp);
    tl.setTabsFromPagerAdapter(downloadManagerAdapter);

  }
}
