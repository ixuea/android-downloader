package cn.woblog.android.downloader.db;

import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_PAUSED;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.woblog.android.downloader.config.Config;
import cn.woblog.android.downloader.domain.DownloadInfo;
import cn.woblog.android.downloader.domain.DownloadThreadInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpingqing on 17/1/23.
 */

public final class DefaultDownloadDBController implements DownloadDBController {


  public static final String[] DOWNLOAD_INFO_COLUMNS = new String[]{"_id", "supportRanges",
      "createAt", "uri",
      "path", "size", "progress",
      "status"};

  public static final String[] DOWNLOAD_THREAD_INFO_COLUMNS = new String[]{"_id", "threadId",
      "downloadInfoId", "uri",
      "start", "end", "progress"};
  public static final String SQL_UPDATE_DOWNLOAD_THREAD_INFO = String.format(
      "REPLACE INTO %s (_id,threadId,downloadInfoId,uri,start,end,progress) VALUES(?,?,?,?,?,?,?);",
      DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO);

  public static final String SQL_UPDATE_DOWNLOAD_INFO = String.format(
      "REPLACE INTO %s (_id,supportRanges,createAt,uri,path,size,progress,status) VALUES(?,?,?,?,?,?,?,?);",
      DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO);

  public static final String SQL_UPDATE_DOWNLOADING_INFO_STATUS = String.format(
      "UPDATE %s SET status=? WHERE status!=?;",
      DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO);

  private final Context context;
  private final DefaultDownloadHelper dbHelper;
  private final SQLiteDatabase writableDatabase;
  private final SQLiteDatabase readableDatabase;

  public DefaultDownloadDBController(Context context, Config config) {
    this.context = context;
    dbHelper = new DefaultDownloadHelper(context, config);
    writableDatabase = dbHelper.getWritableDatabase();
    readableDatabase = dbHelper.getReadableDatabase();
  }

  @SuppressWarnings("No problem")
  @Override
  public List<DownloadInfo> findAllDownloading() {
    Cursor cursor = readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO,
        DOWNLOAD_INFO_COLUMNS, "status!=?", new String[]{
            String.valueOf(STATUS_COMPLETED)}, null, null, "createAt desc");

    List<DownloadInfo> downloads = new ArrayList<>();
    Cursor downloadCursor;
    while (cursor.moveToNext()) {
      DownloadInfo downloadInfo = new DownloadInfo();
      downloads.add(downloadInfo);

      inflateDownloadInfo(cursor, downloadInfo);

      //query download thread info
      downloadCursor = readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO,
          DOWNLOAD_THREAD_INFO_COLUMNS, "downloadInfoId=?", new String[]{
              String.valueOf(downloadInfo.getId())}, null, null, null);
      List<DownloadThreadInfo> downloadThreads = new ArrayList<>();
      while (downloadCursor.moveToNext()) {
        DownloadThreadInfo downloadThreadInfo = new DownloadThreadInfo();
        downloadThreads.add(downloadThreadInfo);
        inflateDownloadThreadInfo(downloadCursor, downloadThreadInfo);
      }

      downloadInfo.setDownloadThreadInfos(downloadThreads);

    }
    return downloads;
  }

  @Override
  public List<DownloadInfo> findAllDownloaded() {
    Cursor cursor = readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO,
        DOWNLOAD_INFO_COLUMNS, "status=?", new String[]{
            String.valueOf(STATUS_COMPLETED)}, null, null, "createAt desc");

    List<DownloadInfo> downloads = new ArrayList<>();
    while (cursor.moveToNext()) {
      DownloadInfo downloadInfo = new DownloadInfo();
      downloads.add(downloadInfo);
      inflateDownloadInfo(cursor, downloadInfo);
    }
    return downloads;
  }

  private void inflateDownloadThreadInfo(Cursor cursor,
      DownloadThreadInfo downloadThreadInfo) {
    downloadThreadInfo.setId(cursor.getInt(0));
    downloadThreadInfo.setThreadId(cursor.getInt(1));
    downloadThreadInfo.setDownloadInfoId(cursor.getInt(2));
    downloadThreadInfo.setUri(cursor.getString(3));
    downloadThreadInfo.setStart(cursor.getLong(4));
    downloadThreadInfo.setEnd(cursor.getLong(5));
    downloadThreadInfo.setProgress(cursor.getLong(6));
  }

  private void inflateDownloadInfo(Cursor cursor, DownloadInfo downloadInfo) {
    downloadInfo.setId(cursor.getInt(0));
    downloadInfo.setSupportRanges(cursor.getInt(1));
    downloadInfo.setCreateAt(cursor.getLong(2));
    downloadInfo.setUri(cursor.getString(3));
    downloadInfo.setPath(cursor.getString(4));
    downloadInfo.setSize(cursor.getLong(5));
    downloadInfo.setProgress(cursor.getLong(6));
    downloadInfo.setStatus(cursor.getInt(7));
  }

  @Override
  public DownloadInfo findDownloadedInfoById(int id) {
    Cursor cursor = readableDatabase
        .query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, DOWNLOAD_INFO_COLUMNS, "_id=?",
            new String[]{String.valueOf(id)},
            null, null, "createAt desc");
    if (cursor.moveToNext()) {
      DownloadInfo downloadInfo = new DownloadInfo();

      inflateDownloadInfo(cursor, downloadInfo);

      return downloadInfo;
    }
    return null;
  }

  @Override
  public void pauseAllDownloading() {
    writableDatabase.execSQL(
        SQL_UPDATE_DOWNLOADING_INFO_STATUS,
        new Object[]{STATUS_PAUSED, STATUS_COMPLETED});
  }

  @Override
  public void createOrUpdate(DownloadInfo downloadInfo) {
    writableDatabase.execSQL(
        SQL_UPDATE_DOWNLOAD_INFO,
        new Object[]{
            downloadInfo.getId(), downloadInfo.getSupportRanges(),
            downloadInfo.getCreateAt(), downloadInfo.getUri(), downloadInfo.getPath(),
            downloadInfo.getSize(), downloadInfo.getProgress(), downloadInfo.getStatus()});
  }

  @Override
  public void createOrUpdate(DownloadThreadInfo downloadThreadInfo) {
    writableDatabase.execSQL(
        SQL_UPDATE_DOWNLOAD_THREAD_INFO,
        new Object[]{
            downloadThreadInfo.getId(),
            downloadThreadInfo.getThreadId(),
            downloadThreadInfo.getDownloadInfoId(),
            downloadThreadInfo.getUri(),
            downloadThreadInfo.getStart(), downloadThreadInfo.getEnd(),
            downloadThreadInfo.getProgress()});
  }

  @Override
  public void delete(DownloadInfo downloadInfo) {
    writableDatabase.delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, "_id=?",
        new String[]{String.valueOf(downloadInfo.getId())});
    writableDatabase
        .delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO, "downloadInfoId=?",
            new String[]{String.valueOf(downloadInfo.getId())});
  }

  @Override
  public void delete(DownloadThreadInfo downloadThreadInfo) {
    writableDatabase
        .delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO, "id=?",
            new String[]{String.valueOf(downloadThreadInfo.getId())});
  }
}
