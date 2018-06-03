package com.technorabit.agritech;

import android.content.DialogInterface;
import android.os.Bundle;

import com.dms.datalayerapi.util.ConnectionUtil;
import com.dms.datalayerapi.util.ParseUtils;
import com.technorabit.agritech.connection.RestClient;
import com.technorabit.agritech.constant.APIConstant;
import com.technorabit.agritech.db.DBUtil;
import com.technorabit.agritech.db.SharedUtil;
import com.technorabit.agritech.fragment.dailog.BaseLoadingFragment;
import com.technorabit.agritech.model.CropsRes;
import com.technorabit.agritech.model.SeasonRes;
import com.technorabit.agritech.model.StateReq;
import com.technorabit.agritech.model.StatesRes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBg();
            }
        });
    }

    private void doInBg() {
        if (ConnectionUtil.checkInternetConnection(this)) {
            StateReq stateReq = new StateReq();
            stateReq.countryId = "99";
            StatesRes state = ParseUtils.getParseObj(RestClient.get(this).diskCacheEnable(true).doPost(APIConstant.GET_STATE, ParseUtils.getJsonString(stateReq)), StatesRes.class);
            if (state != null && state.Content != null && state.Content.size() > 0) {
                DBUtil.getInstance(SplashActivity.this).removeAndInsert(SplashActivity.this, StatesRes.State.class, state.Content);
            }
            CropsRes cropsRes = ParseUtils.getParseObj(RestClient.get(this).diskCacheEnable(true).doGet(APIConstant.GET_CROPS), CropsRes.class);
            if (cropsRes != null && cropsRes.Content != null && cropsRes.Content.size() > 0) {
                DBUtil.getInstance(SplashActivity.this).removeAndInsert(SplashActivity.this, CropsRes.Crop.class, cropsRes.Content);
            }
            SeasonRes seasonRes = ParseUtils.getParseObj(RestClient.get(this).diskCacheEnable(true).doGet(APIConstant.GET_SEASONS), SeasonRes.class);
            if (seasonRes != null && seasonRes.Content != null && seasonRes.Content.size() > 0) {
                DBUtil.getInstance(SplashActivity.this).removeAndInsert(SplashActivity.this, SeasonRes.Season.class, seasonRes.Content);
            }

        } else {
            if (!DBUtil.isDBSplashDataAvailable(this)) {
                BaseLoadingFragment.newInstance(getString(R.string.app_name), getString(R.string.first_data_need_notification), false, true).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                }).show(getSupportFragmentManager(), BaseLoadingFragment.FRAGMENT_TAG);
                return;
            }
        }

        if (!SharedUtil.get(this).getBoolean(SharedUtil.KEY_IS_LOGIN)) {
            startActivity(SplashActivity.this, LoginActivity.class, true);
        } else {
            if (DBUtil.isDBSplashDataAvailable(this)) {
                startActivity(SplashActivity.this, DashboardActivity.class, true);
            }
        }

    }
}
