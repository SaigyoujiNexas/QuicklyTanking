package com.example.modulescore.main.Pre;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.modulescore.main.Run.RunActivity;
import com.example.modulescore.main.Activities.TargetDistanceActivity;
import com.example.modulescore.main.R;

public class PreRunFragment extends Fragment implements View.OnClickListener{
    CardView card_startRun_preRun;
    CardView card_targetDistance_preRun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prerunfragment, container, false);
        card_startRun_preRun = view.findViewById(R.id.card_startRun_preRun);
        card_targetDistance_preRun = view.findViewById(R.id.card_targetDistance_preRun);
        card_targetDistance_preRun.setOnClickListener(this);
        card_startRun_preRun.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_startRun_preRun:
                Intent startRunningIntent = new Intent(getActivity(), RunActivity.class);
                startActivity(startRunningIntent);

                break;
            case R.id.card_targetDistance_preRun:
                Intent targetDistanceIntent = new Intent(getActivity(), TargetDistanceActivity.class);
                startActivity(targetDistanceIntent);
                break;
            default:
                break;
        }
    }
}
