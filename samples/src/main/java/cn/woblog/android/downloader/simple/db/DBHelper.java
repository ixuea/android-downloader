package cn.woblog.android.downloader.simple.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfoLocal;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

/**
 * Created by renpingqing on 17/3/2.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

  private static final String DB_NAME = "/sdcard/d/data.db";
  private static final int DB_VERSION = 1;

  public DBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, MyDownloadInfoLocal.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {
    try {
      TableUtils.dropTable(connectionSource, MyDownloadInfoLocal.class, true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
