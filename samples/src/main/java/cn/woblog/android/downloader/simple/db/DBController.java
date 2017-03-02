package cn.woblog.android.downloader.simple.db;

import android.content.Context;
import cn.woblog.android.downloader.simple.domain.MyDownloadInfoLocal;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

/**
 * Created by renpingqing on 17/3/2.
 */

public class DBController {

  private static DBController instance;
  private final Context context;
  private final DBHelper dbHelper;
  private final Dao<MyDownloadInfoLocal, Integer> myDownloadInfoLocalsDao;

  public DBController(Context context) throws SQLException {
    this.context = context;
    dbHelper = new DBHelper(context);
    try {
      myDownloadInfoLocalsDao = dbHelper.getDao(MyDownloadInfoLocal.class);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static DBController getInstance(Context context) throws SQLException {
    if (instance == null) {
      instance = new DBController(context);
    }
    return instance;
  }


  public void createOrUpdateMyDownloadInfo(MyDownloadInfoLocal downloadInfoLocal)
      throws SQLException {
    myDownloadInfoLocalsDao.createOrUpdate(downloadInfoLocal);
  }

  public int deleteMyDownloadInfo(int id)
      throws SQLException {
    return myDownloadInfoLocalsDao.deleteById(id);
  }

  public MyDownloadInfoLocal findMyDownloadInfoById(int id)
      throws SQLException {
    return myDownloadInfoLocalsDao.queryForId(id);
  }

}
