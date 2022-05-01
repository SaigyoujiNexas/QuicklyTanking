package com.example.modulescore.main.Pre.Mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulescore.main.Pre.Data.PreDataActivity;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulespublic.common.utils.TimeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {
    LinearLayout linearLayout;
    List<View> viewList = new ArrayList<>();
    final String TAG = "MineFragmentTAG";
    String[] titles = {"跑步记录","数据统计","我的购物车","我的收藏","关于我们"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        linearLayout = view.findViewById(R.id.linearlayout_minefragment);
        for(int i = 0;i<5;i++){
            LinearAddView(i);
        }
        viewList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick");
                Intent intent = new Intent(getActivity(), PreDataActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void LinearAddView(int i){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine_item,linearLayout,false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_mine_item);
        ImageView img_item = view.findViewById(R.id.img_mine_item);
        TextView text_item = view.findViewById(R.id.text_mine_item);
        text_item.setText(titles[i]);
        linearLayout.addView(view);
        Log.d("LinearAddView","FINISH");
        viewList.add(view);
    }

}