package cn.woblog.android.downloader.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import cn.woblog.android.downloader.simple.activity.ListActivity;
import cn.woblog.android.downloader.simple.activity.SimpleActivity;
import cn.woblog.android.downloader.simple.adapter.DownloadListAdapter;

/**
 * sample main activity.
 */
public class MainActivity extends AppCompatActivity {

  private RecyclerView rv;
  private DownloadListAdapter downloadListAdapter;

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


}
