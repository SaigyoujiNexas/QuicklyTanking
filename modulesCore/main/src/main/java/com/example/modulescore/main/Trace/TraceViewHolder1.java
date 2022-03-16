package com.example.modulescore.main.Trace;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulespublic.common.base.RunningRecord;

public class TraceViewHolder1 extends RecyclerView.ViewHolder {

    private RunningRecord selectedRecord;
    public TraceViewHolder1(RunningRecord selectedRecord,@NonNull View itemView) {
        super(itemView);
        this.selectedRecord = selectedRecord;
    }

}