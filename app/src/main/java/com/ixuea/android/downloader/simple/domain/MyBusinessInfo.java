package com.ixuea.android.downloader.simple.domain;

import java.io.Serializable;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */
public class MyBusinessInfo implements Serializable {

    private String name;
    private String icon;
    private String url;

    public MyBusinessInfo(String name, String icon, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
