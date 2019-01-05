package com.ixuea.android.downloader.callback;

import java.lang.ref.SoftReference;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public abstract class AbsDownloadListener implements DownloadListener {

    private SoftReference<Object> userTag;

    public AbsDownloadListener() {
    }

    public AbsDownloadListener(SoftReference<Object> userTag) {
        this.userTag = userTag;
    }

    public SoftReference<Object> getUserTag() {
        return userTag;
    }

    public void setUserTag(SoftReference<Object> userTag) {
        this.userTag = userTag;
    }


}
