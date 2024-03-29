package com.ixuea.android.downloader.simple.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ixuea.android.downloader.simple.domain.MyBusinessInfLocal;
import com.ixuea.android.downloader.simple.domain.MyDownloadInfLocal;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    //  private static final String DB_NAME = "/sdcard/d/data.db";
    private static final String DB_NAME = "download_data.db";
    private static final int DB_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MyBusinessInfLocal.class);
            TableUtils.createTable(connectionSource, MyDownloadInfLocal.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MyBusinessInfLocal.class, true);
            TableUtils.dropTable(connectionSource, MyDownloadInfLocal.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
