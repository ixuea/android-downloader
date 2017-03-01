package cn.woblog.android.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Public fragment parent class,all the classes should be inherited.
 */
public abstract class BaseFragment extends Fragment {


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    beforeInit();
  }

  protected void beforeInit() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = getLayoutView(inflater, container, savedInstanceState);
    if (isAutoBind()) {
      bindView(view);
    }
    return view;
  }

  /**
   * 手动bindview,请在合适的位置手动调用
   */
  protected void bindView(View view) {
  }

  /**
   * 是否手动绑定view
   */
  protected boolean isAutoBind() {
    return true;
  }

  /**
   * set fragment layout
   */
  protected abstract View getLayoutView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState);

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    initView();
    initStyle();
    initData();
    initListener();
    super.onViewCreated(view, savedInstanceState);
  }

  /**
   * 找控件
   */
  protected void initView() {

  }

  /**
   * 动态设置样式，颜色，宽高，背景
   */
  protected void initStyle() {
  }

  /**
   * 设置数据
   */
  protected void initData() {

  }

  /**
   * 绑定监听器
   */
  protected void initListener() {
  }

  //activity 条转方法
  public void toActivity(Class<?> clazz) {
    toActivity(new Intent(getActivity(), clazz));
  }

  public void toActivity(Intent intent) {
    startActivity(intent);
  }

  public void toActivityAfterFinishThis(Class<?> clazz) {
    toActivity(clazz);
    getActivity().finish();
  }

  public void toActivityAfterFinishThis(Intent intent) {
    toActivity(intent);
    getActivity().finish();
  }

  public void toActivityForResult(Intent intent, int requestCode) {
    startActivityForResult(intent, requestCode);
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
//		ButterKnife.unbind(this);
  }

}
