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


  protected void bindView(View view) {
  }


  protected boolean isAutoBind() {
    return true;
  }


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


  protected void initView() {

  }


  protected void initStyle() {
  }


  protected void initData() {

  }


  protected void initListener() {
  }

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
// ButterKnife.unbind(this);
  }

}
