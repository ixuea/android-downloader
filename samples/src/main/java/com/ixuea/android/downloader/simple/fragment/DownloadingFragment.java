package com.ixuea.android.downloader.simple.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ixuea.android.common.fragment.BaseFragment;
import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.simple.R;
import com.ixuea.android.downloader.simple.adapter.DownloadAdapter;
import com.ixuea.android.downloader.simple.event.DownloadStatusChanged;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/3/1.
 */

public class DownloadingFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView rv;
    private DownloadAdapter downloadAdapter;
    private DownloadManager downloadManager;
    private Button bt_control_all;
    private Button bt_clear_all;
    private boolean hasDownloading;

    public static DownloadingFragment newInstance() {

        Bundle args = new Bundle();

        DownloadingFragment fragment = new DownloadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloading, null);
    }

    @Override
    protected void initView() {
        super.initView();
        rv = (RecyclerView) getView().findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        bt_control_all = (Button) getView().findViewById(R.id.bt_control_all);
        bt_clear_all = (Button) getView().findViewById(R.id.bt_clear_all);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        downloadManager = DownloadService
                .getDownloadManager(getActivity().getApplicationContext());

        downloadAdapter = new DownloadAdapter(getActivity());
        rv.setAdapter(downloadAdapter);

        fetchData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_control_all.setOnClickListener(this);
        bt_clear_all.setOnClickListener(this);
    }

    @Subscribe
    public void onEventMainThread(DownloadStatusChanged event) {
        fetchData();
    }

    private void fetchData() {
        downloadAdapter.setData(downloadManager.findAllDownloading());
        setPauseOrResumeButtonStatus();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_control_all:

                onPauseAllClick();
                break;
            case R.id.bt_clear_all:
                onDeleteAllClick();
                break;

        }
    }

    private void onDeleteAllClick() {
        if (downloadAdapter.getData().size() == 0) {
            Toast.makeText(getActivity(), "No download task.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (DownloadInfo downloadInfo : downloadAdapter.getData()) {
            downloadManager.remove(downloadInfo);
        }

        downloadAdapter.clearData();
    }

    //暂停所有，开始所有
    private void onPauseAllClick() {
        //无数据，可以按照需求来处理
        if (downloadAdapter.getData().size() == 0) {
            Toast.makeText(getActivity(), "No download task.", Toast.LENGTH_SHORT).show();
            return;
        }

        pauseOrResumeAll();
    }

    private void pauseOrResumeAll() {
        if (hasDownloading) {
            pauseAll();
            hasDownloading = false;
        } else {
            resumeAll();
            hasDownloading = true;
        }

        setPauseOrResumeButtonStatus();
    }

    private void setPauseOrResumeButtonStatus() {
        hasDownloading = false;
        for (DownloadInfo downloadInfo : downloadAdapter.getData()) {
            if (DownloadInfo.STATUS_DOWNLOADING == downloadInfo.getStatus()) {
                //如果有一个的状态是正在下载，按钮就是暂停所有
                hasDownloading = true;
                break;
            }
        }


        if (hasDownloading) {
            bt_control_all.setText("Pause all");
        } else {
            bt_control_all.setText("Start all");
        }
    }

    private void resumeAll() {
        downloadManager.resumeAll();
        reloadData();
    }

    private void pauseAll() {
        downloadManager.pauseAll();
        reloadData();
    }

    private void reloadData() {
        downloadAdapter.notifyDataSetChanged();
    }
}
