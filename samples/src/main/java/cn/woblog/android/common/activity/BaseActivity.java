package cn.woblog.android.common.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by ren on 2015/4/27 0027.
 */
public class BaseActivity extends AppCompatActivity {

  /**
   * Find all view.
   */
  protected void initView() {
  }

  /**
   * Set some style.
   */
  protected void initStyle() {
  }

  /**
   * Set data.
   */
  protected void initData() {

  }

  /**
   * Bind view listener.
   */
  protected void initListener() {
  }

  protected void init() {
    beforeInit();
    initView();
    initStyle();
    initData();
    initListener();
  }

  protected void beforeInit() {

  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    init();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    init();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    init();
  }


  protected FragmentActivity getActivity() {
    return this;
  }


  public void toActivity(Class<?> clazz) {
    toActivity(new Intent(getActivity(), clazz));
  }

  public void toActivity(Intent intent) {
    startActivity(intent);
  }

  public void toActivityAfterFinishThis(Class<?> clazz) {
    toActivity(clazz);
    finish();
  }

  public void toActivityAfterFinishThis(Intent intent) {
    toActivity(intent);
    finish();
  }

  public void toActivityForResult(Intent intent, int requestCode) {
    startActivityForResult(intent, requestCode);
  }


}
