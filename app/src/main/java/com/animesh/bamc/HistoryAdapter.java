package com.animesh.bamc;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.animesh.bamc.R;


import com.animesh.bamc.Interface.RecycleClickListener;

import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Animesh on 10/19/2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private static final String TAG ="HistoryAdaptor" ;
    LinkedHashMap<String, Integer> value = new LinkedHashMap<>();
    String key[]={"28/10",
            "02/11",
            "03/11",
            "04/11",
            "05/11",
            "06/11",
            "07/11",
            "08/11",
            "09/11",
            "10/11",
            "11/11",
            "12/11",
            "13/11",
            "14/11"};
    public LayoutInflater inflater;
    RecycleClickListener clickListener;

    public HistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.value=MainActivity.value;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.historyrow, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    public void setClickListener(RecycleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e(TAG,"key:"+key[position]);
        if (value.containsKey(key[position])) {
            holder.calorie.setText(value.get(key[position])+" Cal");
            Log.e(TAG,"key value:"+value.get(key[position])+" Cal");
           // calories.setText(String.valueOf(value.get(key)));
        } else {
            holder.calorie.setText(String.valueOf(0)+" Cal");
            Log.e(TAG,"zero not key");
            //calories.setText(String.valueOf(0));
        }


        try {
            String strDate=key[position];
            SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM");
            Date date = null;
            date = sdfSource.parse(strDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("EEE, MMM d");
            strDate = sdfDestination.format(date);
            holder.date.setText(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return key.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date,calorie;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvdate);
            calorie=(TextView) itemView.findViewById(R.id.calcount);
            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getLayoutPosition());
                    }
                });
            }
        }
    }
}
