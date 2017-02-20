package cn.woblog.android.downloader.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;

/**
 * Created by renpingqing on 17/1/23.
 */

public class DefaultDownloadHelper extends SQLiteOpenHelper {

  private static final String CREATE_DOWNLOAD_TABLE_SQL = "CREATE TABLE download_info (_id INTEGER PRIMARY key,id VARCHAR(255),supportRanges INTEGER,createAt LONG,url VARCHAR(255),path VARCHAR(255),size LONG, progress LONG,status INT);";
  private static final String CREATE_DOWNLOAD_THREAD_TABLE_SQL = "CREATE TABLE download_thread (_id INTEGER PRIMARY KEY,downloadkey INTEGER,threadId INTEGER,url VARCHAR(255),start LONG,end LONG,progress LONG);";
  private static final int DB_VERSION = 1;
  private static final String DB_NAME = "/sdcard/download_info.db";
//  private static final String DB_NAME = "download_info.db";

  public DefaultDownloadHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @RequiresApi(api = VERSION_CODES.HONEYCOMB)
  public DefaultDownloadHelper(Context context, String name,
      CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
    super(context, name, factory, version, errorHandler);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    createTable(db);
  }

  private void createTable(SQLiteDatabase db) {
    db.execSQL(CREATE_DOWNLOAD_TABLE_SQL);
    db.execSQL(CREATE_DOWNLOAD_THREAD_TABLE_SQL);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //TODO upgrade database
  }

//  CREATE TABLE download_info (
//      _id INTEGER PRIMARY key,
//      id VARCHAR(255),
//      supportRanges INTEGER,
//      createAt LONG,
//      url VARCHAR(255),
//      path VARCHAR(255),
//      size LONG,
//      progress LONG,
//      status INT
//  );

//  CREATE TABLE download_thread (
//      _id INTEGER PRIMARY KEY,
//      downloadkey INTEGER,
//      threadId INTEGER,
//      url VARCHAR(255),
//      start LONG,
//      end LONG,
//      progress LONG
//  );

}




