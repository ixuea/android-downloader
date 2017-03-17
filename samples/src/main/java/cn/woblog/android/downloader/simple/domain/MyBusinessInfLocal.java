package cn.woblog.android.downloader.simple.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by renpingqing on 17/3/2.
 */
@DatabaseTable(tableName = "MyBusinessInfLocal")
public class MyBusinessInfLocal {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField
  private String name;

  @DatabaseField
  private String icon;

  @DatabaseField
  private String url;

  public MyBusinessInfLocal() {
  }

  public MyBusinessInfLocal(int id, String name, String icon, String url) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
