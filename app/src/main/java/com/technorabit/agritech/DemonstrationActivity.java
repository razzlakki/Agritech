package com.technorabit.agritech;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class DemonstrationActivity extends AppCompatActivity {

    private ScrollView svDemonstration;
    private LinearLayout llyDemonstration;

    private EditText etFarmerSerch;
    private EditText etSymptoms;
    private EditText etProductUsed;
    private EditText etProductSuggested;
    private EditText etDosage;
    private EditText etQuantity;
    private EditText etCropPic;
    private EditText etRemarks;

    private Spinner spinnerCrop;
    private Spinner spinnerCropProblem;
    private Spinner spinnerIntegrityScale;
    private Spinner spinnerFramerGrade;

    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        svDemonstration = findViewById(R.id.sv_demonstration);
        llyDemonstration = findViewById(R.id.lly_demonstration);

        etFarmerSerch =findViewById(R.id.et_farmer_serch);
        etSymptoms = findViewById(R.id.et_symptoms);
etProductUsed = findViewById(R.id.et_product_used);
etProductSuggested = findViewById(R.id.et_product_suggested);
etDosage = findViewById(R.id.et_dosage);
etQuantity = findViewById(R.id.et_quantity);
etCropPic = findViewById(R.id.et_crop_pic);
        etRemarks = findViewById(R.id.et_remarks);

        spinnerCrop = findViewById(R.id.spinner_crop);
        spinnerCropProblem = findViewById(R.id.spinner_crop_problem);
        spinnerIntegrityScale = findViewById(R.id.spinner_integrity_scale);
        spinnerFramerGrade = findViewById(R.id.spinner_framer_grade);
        btnSubmit = findViewById(R.id.btn_farmer_submit);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(DemonstrationActivity.this,"submit",Toast.LENGTH_SHORT).show();
        }
    });
    }

}
