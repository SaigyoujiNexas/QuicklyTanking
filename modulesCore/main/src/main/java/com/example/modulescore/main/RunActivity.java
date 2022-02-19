package com.example.modulescore.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RunActivity extends BaseActivity implements View.OnClickListener {
    FloatingActionButton startRunButton;
    FloatingActionButton stopRunButton;
    FloatingActionButton finishRunButton;
    CardView toMapCard;
    boolean isRun = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        startRunButton = findViewById(R.id.startRunButton);
        stopRunButton = findViewById(R.id.stopRunButton);
        finishRunButton = findViewById(R.id.finishRunButton);
        toMapCard = findViewById(R.id.toMapCard);
        startRunButton.hide();
        finishRunButton.hide();
        //设置监听器
        startRunButton.setOnClickListener(this);
        stopRunButton.setOnClickListener(this);
        finishRunButton.setOnClickListener(this);
        toMapCard.setOnClickListener(this);
        //ColorStateList stopColor = ContextCompat.getColorStateList(getApplicationContext(), R.color.red);
        //isRunColor = ContextCompat.getColorStateList(getApplicationContext(), R.color.green);
//        stopRunButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isRun == true) {
//                    isRun = false;
//                    //startRunButton.setBackgroundTintList(stopColor);
//                    stopRunButton.hide();
//                    startRunButton.show();
//                    finishRunButton.show();
//                }else if(isRun == false){
//                    isRun = true;
//                    stopRunButton.show();
//                    startRunButton.hide();
//                    finishRunButton.hide();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default: {
                Log.d("11111", "!00");
                break;
            }
            case R.id.stopRunButton:
            case R.id.finishRunButton:
            case R.id.startRunButton:{
                Log.d("11111", "!11");
                if (isRun == true) {
                    isRun = false;
                    Log.d("11111", "!22");
                    //startRunButton.setBackgroundTintList(stopColor);
                    stopRunButton.hide();
                    startRunButton.show();
                    finishRunButton.show();
                } else if (isRun == false) {
                    isRun = true;
                    Log.d("11111", "!33");
                    stopRunButton.show();
                    startRunButton.hide();
                    finishRunButton.hide();
                }
                break;
            }
            case R.id.toMapCard:
                Intent intent = new Intent(this, My.RunningActivity.class);
                startActivity(intent);
                break;
        }
    }
}