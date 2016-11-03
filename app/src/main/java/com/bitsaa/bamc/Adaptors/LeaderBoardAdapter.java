package com.bitsaa.bamc.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitsaa.bamc.R;

import com.bitsaa.bamc.Interface.Participant_Details;
import com.bitsaa.bamc.Interface.RecycleClickListener;

import java.util.List;

/**
 * Created by Animesh on 10/19/2016.
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
    List<Participant_Details> ParticipantsList;
    public LayoutInflater inflater;
    RecycleClickListener clickListener;

    public LeaderBoardAdapter(Context context, List<Participant_Details> ParticipantsList) {
        inflater = LayoutInflater.from(context);
        this.ParticipantsList = ParticipantsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.participants_cards, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    public void setClickListener(RecycleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(ParticipantsList.get(position).getName());
        holder.rank.setText(ParticipantsList.get(position).getRank());
        holder.score.setText(ParticipantsList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return ParticipantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,score,rank;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.participant_name);
            rank=(TextView) itemView.findViewById(R.id.rank);
            score=(TextView) itemView.findViewById(R.id.score);
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
