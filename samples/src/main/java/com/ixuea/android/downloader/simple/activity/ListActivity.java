package com.ixuea.android.downloader.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ixuea.android.common.activity.BaseActivity;
import com.ixuea.android.common.adapter.BaseRecyclerViewAdapter.OnItemClickListener;
import com.ixuea.android.downloader.simple.R;
import com.ixuea.android.downloader.simple.adapter.DownloadListAdapter;
import com.ixuea.android.downloader.simple.domain.MyBusinessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * How to use Android Downloader in RecyclerView.
 */
public class ListActivity extends BaseActivity implements OnItemClickListener {

    private static final int REQUEST_DOWNLOAD_DETAIL_PAGE = 100;

    private RecyclerView rv;
    private DownloadListAdapter downloadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


    }

    @Override
    public void initListener() {
        downloadListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        downloadListAdapter = new DownloadListAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(downloadListAdapter);

        downloadListAdapter.setData(getDownloadListData());
    }

    private List<MyBusinessInfo> getDownloadListData() {
        ArrayList<MyBusinessInfo> myBusinessInfos = new ArrayList<>();
        myBusinessInfos.add(new MyBusinessInfo("QQ",
                "http://img.wdjimg.com/mms/icon/v1/4/c6/e3ff9923c44e59344e8b9aa75e948c64_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/e/b8/520c1a2208bf7724b96f538247233b8e.apk"));
        myBusinessInfos.add(new MyBusinessInfo("微信",
                "http://img.wdjimg.com/mms/icon/v1/7/ed/15891412e00a12fdec0bbe290b42ced7_256_256.png",
                "http://wdj-uc1-apk.wdjcdn.com/1/a3/8ee2c3f8a6a4a20116eed72e7645aa31.apk"));
        myBusinessInfos.add(new MyBusinessInfo("陌陌",
                "http://img.wdjimg.com/mms/icon/v1/a/6e/03d4e21876706e6a175ff899afd316ea_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/b/0a/369eec172611626efff4e834fedce0ab.apk"));
        myBusinessInfos.add(new MyBusinessInfo("美颜相机",
                "http://img.wdjimg.com/mms/icon/v1/7/7b/eb6b7905241f22b54077cbd632fe87b7_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/a/e9/618d265197a43dab6277c41ec5f72e9a.apk"));
        myBusinessInfos.add(new MyBusinessInfo("Chrome",
                "http://img.wdjimg.com/mms/icon/v1/d/fd/914f576f9fa3e9e7aab08ad0a003cfdd_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/6/0d/6e93a829b97d671ee56190aec78400d6.apk"));
        myBusinessInfos.add(new MyBusinessInfo("淘宝",
                "https://android-artworks.25pp.com/fs08/2018/11/28/9/110_5eb0ac0aebf3abcf91590b8a0a320630_con_130x130.png",
                "https://alissl.ucdl.pp.uc.cn/fs08/2018/11/27/9/110_7a50865b726e729b6edc31cfd724c7b7.apk"));
        myBusinessInfos.add(new MyBusinessInfo("手机天猫",
                "https://android-artworks.25pp.com/fs08/2018/11/22/11/110_c6ff596ba84d12ba188749fd6b57808c_con_130x130.png",
                "https://alissl.ucdl.pp.uc.cn/fs08/2018/11/22/5/110_e3f773e7c9b1182424c73f538f9a74b4.apk"));
        myBusinessInfos.add(new MyBusinessInfo("支付宝",
                "https://android-artworks.25pp.com/fs08/2018/11/23/7/110_ba7b3b9f5b1c2c460a5ad1f4ae8ce9e0_con_130x130.png",
                "https://alissl.ucdl.pp.uc.cn/fs08/2018/12/13/5/1_5054c6afc3449aeb24971054a8c88675.apk"));
        return myBusinessInfos;
    }

    @Override
    public void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void onItemClick(int position) {
        MyBusinessInfo data = downloadListAdapter.getData(position);
        Intent intent = new Intent(this, DownloadDetailActivity.class);
        intent.putExtra(DownloadDetailActivity.DATA, data);
        startActivityForResult(intent, REQUEST_DOWNLOAD_DETAIL_PAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        downloadListAdapter.notifyDataSetChanged();
    }
}
