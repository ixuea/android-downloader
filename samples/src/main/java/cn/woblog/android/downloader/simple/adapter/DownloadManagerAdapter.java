package cn.woblog.android.downloader.simple.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import cn.woblog.android.common.adapter.CustomFragmentPagerAdapter;
import cn.woblog.android.downloader.simple.fragment.DownloadedFragment;
import cn.woblog.android.downloader.simple.fragment.DownloadingFragment;

/**
 * Created by renpingqing on 17/3/1.
 */

public class DownloadManagerAdapter extends CustomFragmentPagerAdapter<String> {


  public DownloadManagerAdapter(FragmentManager fm, Context context) {
    super(fm, context);
  }

  @Override
  public Fragment getItem(int position) {
    if (position == 0) {
      return DownloadingFragment.newInstance();
    } else {
      return DownloadedFragment.newInstance();
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return getData(position);
  }
}
