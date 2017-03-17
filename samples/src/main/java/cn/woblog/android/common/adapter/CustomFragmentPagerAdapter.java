package cn.woblog.android.common.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ren on 2015/12/3 0003.
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
