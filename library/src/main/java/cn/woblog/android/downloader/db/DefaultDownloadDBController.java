package cn.woblog.android.downloader.db;

import static cn.woblog.android.downloader.domain.DownloadInfo.STATUS_COMPLETED;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.woblog.android.downloader.domain.DownloadInfo;
import cn.woblog.android.downloader.domain.DownloadThreadInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpingqing on 17/1/23.
 */

public class DefaultDownloadDBController implements DownloadDBController {


  private final Context context;
  private final DefaultDownloadHelper dbHelper;
  private final SQLiteDatabase writableDatabase;
  private final SQLiteDatabase readableDatabase;

  public DefaultDownloadDBController(Context context) {
    this.context = context;
    dbHelper = new DefaultDownloadHelper(context);
    writableDatabase = dbHelper.getWritableDatabase();
    readableDatabase = dbHelper.getReadableDatabase();
  }

  @SuppressWarnings("No problem")
  @Override
  public List<DownloadInfo> findAllDownloading() {
    Cursor cursor = readableDatabase.query("download_info",
        new String[]{"_id", "supportRanges", "createAt", "uri", "path", "size", "progress",
            "status"}, "status!=?", new String[]{
            String.valueOf(STATUS_COMPLETED)}, null, null, "createAt desc");

    List<DownloadInfo> downloads = new ArrayList<>();
    while (cursor.moveToNext()) {
      DownloadInfo downloadInfo = new DownloadInfo();
      downloads.add(downloadInfo);

      inflateDownloadInfo(cursor, downloadInfo);

    }
    return downloads;
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
    Cursor cursor = readableDatabase.query("download_info",
        new String[]{"_id", "supportRanges", "createAt", "uri", "path", "size", "progress",
            "status"}, "_id=?", new String[]{String.valueOf(id)}, null, null, "createAt desc");
    if (cursor.moveToNext()) {
      DownloadInfo downloadInfo = new DownloadInfo();

      inflateDownloadInfo(cursor, downloadInfo);

      return downloadInfo;
    }
    return null;
  }

  @Override
  public void createOrUpdate(DownloadInfo downloadInfo) {
    writableDatabase.execSQL(
        "REPLACE INTO download_info(_id,supportRanges,createAt,uri,path,size,progress,status) VALUES(?,?,?,?,?,?,?,?);",
        new Object[]{
            downloadInfo.getId(), downloadInfo.getSupportRanges(),
            downloadInfo.getCreateAt(), downloadInfo.getUri(), downloadInfo.getPath(),
            downloadInfo.getSize(), downloadInfo.getProgress(), downloadInfo.getStatus()});
  }

  @Override
  public void createOrUpdate(DownloadThreadInfo downloadThreadInfo) {
    writableDatabase.execSQL(
        "REPLACE INTO download_thread_info(_id,threadId,downloadInfoId,uri,start,end,progress) VALUES(?,?,?,?,?,?,?);",
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

  }

  @Override
  public void delete(DownloadThreadInfo download) {

  }
}
