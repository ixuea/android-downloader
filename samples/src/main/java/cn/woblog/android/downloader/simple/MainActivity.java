package cn.woblog.android.downloader.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.woblog.android.common.activity.BaseActivity;
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
