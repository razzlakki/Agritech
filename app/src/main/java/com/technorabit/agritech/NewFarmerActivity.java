package com.technorabit.agritech;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewFarmerActivity extends AppCompatActivity {

    private ScrollView scrollViewLoginForm;
    private LinearLayout llyLoginForm;

    private EditText etFirstName;
    private EditText etMiddleName;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private EditText etMandalName;
    private EditText etDistrictName;
    private EditText etLandArea;
    private EditText etCropDetails;
    private EditText etRemarks;

    private Spinner spinnerSeasonName;
    private Button btnFarmerSubmit;


    private AutoCompleteTextView actvVillageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_farmer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initGUI();
    }

    private void initGUI() {
        scrollViewLoginForm = findViewById(R.id.login_form);
        llyLoginForm = findViewById(R.id.lly_farmer_form);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etMiddleName = findViewById(R.id.et_middle_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etMandalName = findViewById(R.id.et_mandal_name);
        etDistrictName = findViewById(R.id.et_district_name);
        etLandArea = findViewById(R.id.et_land_area);
        etCropDetails = findViewById(R.id.et_crop_details);
        etRemarks = findViewById(R.id.et_remarks);

        spinnerSeasonName = findViewById(R.id.spinner_season_name);
        btnFarmerSubmit = findViewById(R.id.btn_farmer_submit);

        btnFarmerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewFarmerActivity.this,"farmer submitted",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
