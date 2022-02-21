package com.example.modulescore.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PreRunActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView img_setting;
    ImageView img_close;
    CardView card_startRun_preRun;
    CardView card_targetDistance_preRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_run);
        img_setting = findViewById(R.id.image_setting_prerun);
        img_close = findViewById(R.id.image_close_prerun);
        img_close.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        card_startRun_preRun = findViewById(R.id.card_startRun_preRun);
        card_targetDistance_preRun = findViewById(R.id.card_targetDistance_preRun);
        card_targetDistance_preRun.setOnClickListener(this);
        card_startRun_preRun.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_close_prerun:
                CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
                IntentFilter intentFilter = new IntentFilter("closeactivity");
                registerReceiver(closeReceiver, intentFilter);
                Intent intent = new Intent();
                intent.setAction("closeactivity");
                sendBroadcast(intent);
                break;
            case R.id.image_setting_prerun:

                break;
            case R.id.card_startRun_preRun:
                 Intent startRunIntent = new Intent(this, RunActivity.class);
                 startActivity(startRunIntent);
                break;
            case R.id.card_targetDistance_preRun:
                Intent targetDistanceIntent = new Intent(this, TargetDistanceActivity.class);
                startActivity(targetDistanceIntent);
                break;
            default:
                break;
        }
    }
    public class CloseActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            PreRunActivity.this.finish();
        }
    }
}