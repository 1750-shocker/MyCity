package com.example.smartcity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.bean.ServiceTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class MDBHelper extends OrmLiteSqliteOpenHelper {
    private static MDBHelper mdbHelper = null;
    private final static String DB_NAME = "smart_city.db";
    private final static int DB_VERSION = 1;
    private Dao<RowsDTO, Integer> newsDao = null;
    private Dao<ServiceTable, Integer> servDao = null;

    public MDBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RowsDTO.class);
            TableUtils.createTable(connectionSource, ServiceTable.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    public static MDBHelper getInstance(Context context) {
        if (mdbHelper == null) {
            mdbHelper = new MDBHelper(context, DB_NAME, null, DB_VERSION);
        }
        return mdbHelper;
    }

    public Dao<RowsDTO, Integer> getNewsDao() {
        if (newsDao == null) {
            Dao<RowsDTO, Integer> dao = null;
            try {
                dao = getDao(RowsDTO.class);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            newsDao = dao;
        }
        return newsDao;
    }

    public Dao<ServiceTable, Integer> getServDao() {
        if (servDao == null) {
            Dao<ServiceTable, Integer> dao = null;
            try {
                dao = getDao(ServiceTable.class);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            servDao = dao;
        }
        return servDao;
    }

}
