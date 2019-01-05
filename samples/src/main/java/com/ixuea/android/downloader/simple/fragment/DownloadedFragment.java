package com.ixuea.android.downloader.simple.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ixuea.android.common.fragment.BaseFragment;
import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.simple.R;
import com.ixuea.android.downloader.simple.adapter.DownloadAdapter;
import com.ixuea.android.downloader.simple.event.DownloadStatusChanged;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/3/1.
 */

public class DownloadedFragment extends BaseFragment {

    private RecyclerView rv;
    private DownloadAdapter downloadAdapter;
    private DownloadManager downloadManager;

    public static DownloadedFragment newInstance() {

        Bundle args = new Bundle();

        DownloadedFragment fragment = new DownloadedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloaded, null);
    }

    @Override
    protected void initView() {
        super.initView();
        rv = (RecyclerView) getView().findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);

        downloadManager = DownloadService
                .getDownloadManager(getActivity().getApplicationContext());

        downloadAdapter = new DownloadAdapter(getActivity());
        rv.setAdapter(downloadAdapter);

        //downloading info

        fetchData();
    }

    private void fetchData() {
        downloadAdapter.setData(downloadManager.findAllDownloaded());
    }

    @Subscribe
    public void onEventMainThread(DownloadStatusChanged event) {
        fetchData();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
