package com.xupt.modulescore.main.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.modulescore.main.R;

public class StatisticsActivity extends AppCompatActivity {
    LineChartView lineChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        lineChartView = findViewById(R.id.linechartview_statisticactivity);
    }
}