package com.example.quicklytanking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.repository.RoutePathRepository;

@Route(path = RoutePathRepository.APP)
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v ->
            ARouter.getInstance().build(RoutePathRepository.LOG_IN).navigation(this)
        );
    }
}