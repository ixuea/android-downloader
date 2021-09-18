package com.ixuea.android.downloader.simple.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                "https://pp.myapp.com/ma_icon/0/icon_6633_1631677952/96",
                "https://3b8637d9f6c334dab555e2afbdc16687.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/49F7A4E3B47E5828D02B2A10C580DB65.apk"));
        myBusinessInfos.add(new MyBusinessInfo("微信",
                "http://pp.myapp.com/ma_icon/0/icon_10910_1631077605/96",
                "https://21efcbaa5aca2783174d5e61409a56a4.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/DDF95F1C8F3421C3FD3D54AB4131284B.apk"));
        myBusinessInfos.add(new MyBusinessInfo("MOMO陌陌",
                "https://pp.myapp.com/ma_icon/0/icon_11381_1631702855/96",
                "https://6dbc06d1fdb4b9a104ca2a1f329d9ddc.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/E516E96AAC49419C7228983ED385A9D1.apk"));
        myBusinessInfos.add(new MyBusinessInfo("美颜相机",
                "https://pp.myapp.com/ma_icon/0/icon_1176832_1631606471/96",
                "https://d05e4fda19affc5d56fcc4706a8ae753.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/76826B5F2089F8A817E178947A354E1D.apk"));
        myBusinessInfos.add(new MyBusinessInfo("Chrome",
                "https://pp.myapp.com/ma_icon/0/icon_74260_1609923779/96",
                "https://637c54c96c2bd836f01af683939267ac.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/177FCB82643B8801AE50B9C2266C320C.apk"));
        myBusinessInfos.add(new MyBusinessInfo("淘宝",
                "https://pp.myapp.com/ma_icon/0/icon_5080_1630321832/96",
                "https://imtt.dd.qq.com/16891/apk/AE156F3160FDFD7AF900F3D2DCF0581D.apk"));
        myBusinessInfos.add(new MyBusinessInfo("手机天猫",
                "https://pp.myapp.com/ma_icon/0/icon_208787_1630818334/96",
                "https://imtt.dd.qq.com/16891/apk/8390758CC8FF6371B78E22C953EDE59E.apk"));
        myBusinessInfos.add(new MyBusinessInfo("支付宝",
                "http://pp.myapp.com/ma_icon/0/icon_5294_1631934458/96",
                "https://e50d9cb5335f94b56f730c9778f0af9e.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/94D8AB3EFA5C7FD345ABFCED1B9B90E0.apk"));
        myBusinessInfos.add(new MyBusinessInfo("和平精英-大文件",
                "https://pp.myapp.com/ma_icon/0/icon_52575843_1631064949/96",
                "https://a2b7a617e94763717ccf697f697eccf4.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/DAE19B232D28B8EC63045162C1CE22F5.apk"));
        return myBusinessInfos;
    }

    @Override
    public void initView() {
        rv = findViewById(R.id.rv);
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
