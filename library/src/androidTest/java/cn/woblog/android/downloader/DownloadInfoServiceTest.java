package cn.woblog.android.downloader;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import cn.woblog.android.downloader.callback.DownloadManager;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by renpingqing on 15/01/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DownloadInfoServiceTest {

  @Test
  public void getDownloadManager() throws Exception {
    Context appContext = InstrumentationRegistry.getTargetContext();
    DownloadManager downloadManager1 = DownloadService.getDownloadManager(appContext);
    DownloadManager downloadManager2 = DownloadService.getDownloadManager(appContext);

    assertEquals(downloadManager1, downloadManager2);
  }

}
