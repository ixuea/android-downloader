package com.ixuea.android.downloader.simple.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ixuea.android.common.adapter.CustomFragmentPagerAdapter;
import com.ixuea.android.downloader.simple.fragment.DownloadedFragment;
import com.ixuea.android.downloader.simple.fragment.DownloadingFragment;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
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
