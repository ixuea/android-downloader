package cn.woblog.android.downloader.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.woblog.android.common.activity.BaseActivity;
import cn.woblog.android.downloader.DownloadService;
import cn.woblog.android.downloader.config.Config;
import cn.woblog.android.downloader.simple.activity.DownloadManagerActivity;
import cn.woblog.android.downloader.simple.activity.ListActivity;
import cn.woblog.android.downloader.simple.activity.SimpleActivity;

/**
 * sample main activity.
 */
public class MainActivity extends BaseActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  @Override
  protected void initData() {
    super.initData();
    Config config = new Config();
    config.setDatabaseName("/sdcard/a/d.db"); //set database path.
    config.setDownloadThread(3); //set download quantity at the same time.
    config.setEachDownloadThread(2); //set each download info thread number
    config.setConnectTimeout(10000); // set connect timeout,unit millisecond
    config.setReadTimeout(10000); // set read data timeout,unit millisecond
    DownloadService.getDownloadManager(this.getApplicationContext(), config);
  }


  public void downloadAFile(View view) {
    startActivity(new Intent(this, SimpleActivity.class));
  }

  public void useInList(View view) {
    startActivity(new Intent(this, ListActivity.class));
  }

  public void downloadManager(View view) {
    startActivity(new Intent(this, DownloadManagerActivity.class));
  }


}
