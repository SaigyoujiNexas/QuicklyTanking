package com.example.modulescore.main.Identification;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;
import com.example.modulespublic.common.net.ResultBean;

import java.util.ArrayList;
import java.util.List;

public class AdapterIdentification extends RecyclerView.Adapter<AdapterIdentification.ViewHolderIdentification> {
    List<ResultBean> resultBeanList;
    private List<View> viewList = new ArrayList<>();
    final String TAG =  "AdapterIdentificationTAG";

    public AdapterIdentification(List<ResultBean> resultBeanList) {
        this.resultBeanList = resultBeanList;
        Log.d(TAG,"size:"+resultBeanList.size());
        Log.d(TAG,resultBeanList.get(0).getName()+","+resultBeanList.get(0).toString());
    }

    @NonNull
    @Override
    public ViewHolderIdentification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_identification,parent,false);
        ViewHolderIdentification viewHolderIdentification = new ViewHolderIdentification(view);
        return viewHolderIdentification;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIdentification holder, int position) {
        ResultBean resultBean = resultBeanList.get(position);
        holder.dishname_identificationitem.setText(resultBean.getName());
        Log.d(TAG,""+position);
        if(resultBean.getBaike_info()!=null) {
            holder.baikeurl_identification.setText(resultBean.getBaike_info().getBaike_url());
            holder.description_identification.setText(resultBean.getBaike_info().getDescription());
        }else {
            viewList.get(position).findViewById(R.id.baikeurl_identification).setVisibility(View.GONE);
            viewList.get(position).findViewById(R.id.description_identification).setVisibility(View.GONE);
        }
        holder.calorietext_runrecoritem_identification.setText(resultBean.getCalorie());
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    int count = 0;
    public class ViewHolderIdentification extends RecyclerView.ViewHolder {
        TextView dishname_identificationitem;
        TextView baikeurl_identification;
        TextView description_identification;
        TextView calorietext_runrecoritem_identification;
        public ViewHolderIdentification(@NonNull View itemView) {
            super(itemView);
            description_identification = itemView.findViewById(R.id.description_identification);
            baikeurl_identification = itemView.findViewById(R.id.baikeurl_identification);
            if(count != 0){
                description_identification.setVisibility(View.GONE);
                baikeurl_identification.setVisibility(View.GONE);
            }else {
                count++;
            }
            calorietext_runrecoritem_identification = itemView.findViewById(R.id.calorietext_runrecoritem_identification);
            dishname_identificationitem = itemView.findViewById(R.id.dishname_identificationitem);
            viewList.add(itemView);
        }
    }
}
