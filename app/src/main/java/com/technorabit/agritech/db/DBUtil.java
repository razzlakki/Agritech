package com.technorabit.agritech.db;

import android.content.Context;

import com.dms.datalayerapi.db.DBAction;
import com.dms.datalayerapi.db.DBSupportUtil;
import com.dms.datalayerapi.db.core.TableDetails;
import com.technorabit.agritech.model.CropsRes;
import com.technorabit.agritech.model.LoginRes;
import com.technorabit.agritech.model.SeasonRes;
import com.technorabit.agritech.model.StatesRes;

import java.util.ArrayList;


/**
 * Created by Raja.p on 09-08-2016.
 */
public class DBUtil extends DBSupportUtil {

    private static final int DB_VERSION_NUMBER = 1;
    public static final String DB_NAME = "db_file.writableDB";
    private static DBUtil instance = null;
    private static boolean nonSingleInstance = false;


    private DBUtil(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<TableDetails> getAllTableDetails(ArrayList<TableDetails> allTableDefinitions) {
        allTableDefinitions.add(TableDetails.getTableDetails(StatesRes.State.class));
        allTableDefinitions.add(TableDetails.getTableDetails(LoginRes.LoginData.class));
        allTableDefinitions.add(TableDetails.getTableDetails(CropsRes.Crop.class));
        allTableDefinitions.add(TableDetails.getTableDetails(SeasonRes.Season.class));
        allTableDefinitions.add(TableDetails.getTableDetails(SeasonRes.Season.class));
        return allTableDefinitions;
    }

    public static DBUtil getWDInstance(Context context) {
        return getInstance(context);
    }

    public synchronized static DBUtil getInstance(Context context) {
        if (instance == null) {
            instance = new DBUtil(context);
        }
        return instance;
    }

    @Override
    protected String getDatabaseFileName() {
        return DB_NAME;
    }

    @Override
    public int getDatabaseVersion() {
        return DB_VERSION_NUMBER;
    }


    public <T> void removeAndInsert(Context context, Class<T> classType, T obj) {
        if (obj == null)
            return;
        if (DBUtil.getInstance(context).getCountFromTable(classType) > 0) {
            DBUtil.getInstance(context).dropTable(classType);
        }
        DBUtil.getInstance(context).insertOrUpdateTable(obj, DBAction.INSERT, null);

    }

    public <T> void removeAndInsert(Context context, Class<T> classType, ArrayList<T> objs) {
        if (objs == null && objs.size() == 0)
            return;
        if (DBUtil.getInstance(context).getCountFromTable(classType) > 0) {
            DBUtil.getInstance(context).dropTable(classType);
        }
        DBUtil.getInstance(context).bulkInsertion(objs);
    }

    public <T> void insertBulk(Context context, ArrayList<T> objs) {
        if (objs == null && objs.size() == 0)
            return;
        DBUtil.getInstance(context).bulkInsertion(objs);
    }

    public <T> void insertBulk(ArrayList<T> objs) {
        if (objs == null && objs.size() == 0)
            return;
        try {
            writableDB.beginTransaction();
            for (T t : objs) {
                insertOrUpdateTable(t, DBAction.INSERT, null);
            }
            writableDB.setTransactionSuccessful();
        } finally {
            writableDB.endTransaction();
        }
    }


    public static boolean isDBSplashDataAvailable(Context context) {
        if (DBUtil.getInstance(context).getCountFromTable(StatesRes.State.class) > 0
                && DBUtil.getInstance(context).getCountFromTable(CropsRes.Crop.class) > 0)
            return true;
        return false;
    }

}
