package com.ixuea.android.common.adapter;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 *
 * @param <T> item data type.
 */
public abstract class CustomFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected final List<T> datas = new ArrayList<T>();
    protected final Context context;
    protected T data;

    public CustomFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public T getData(int position) {
        return datas.get(position);
    }

    public void setData(List<T> data) {
        if (data != null && data.size() > 0) {
            datas.clear();
            datas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        datas.clear();
    }

    public void clearAndNotify() {
        datas.clear();
        notifyDataSetChanged();
    }
}
