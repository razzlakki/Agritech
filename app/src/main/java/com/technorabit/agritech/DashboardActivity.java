package com.technorabit.agritech;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Agri Tech");
        setSupportActionBar(toolbar);
        initGUI();
    }

    private void initGUI() {
        findViewById(R.id.demonstration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DashboardActivity.this, FarmerListActivity.class, false);
            }
        });
        findViewById(R.id.formerList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DashboardActivity.this, DemonstrationActivity.class, false);
            }
        });
        findViewById(R.id.competitor_analysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DashboardActivity.this, CompetitorActivity.class, false);
            }
        });
    }

}
