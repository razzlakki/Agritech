package com.dms.datalayerapi.network.executors;

import android.app.AlarmManager;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Raja.p on 17-11-2016.
 */

public class CommonPoolExecutor {

    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static CommonPoolExecutor instance;
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.MINUTES;
    private ThreadPoolExecutor mDecodeThreadPool;

    public static CommonPoolExecutor get() {
        if (instance == null)
            instance = new CommonPoolExecutor();
        return instance;
    }

    private CommonPoolExecutor() {
        LinkedBlockingQueue<Runnable> mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        // Creates a thread pool manager
        mDecodeThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue);
    }

    public void startDownload(Runnable runnable) {
        mDecodeThreadPool.execute(runnable);
    }

}
