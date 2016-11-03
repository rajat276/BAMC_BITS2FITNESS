package com.bitsaa.bamc.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitsaa.bamc.Adaptors.LeaderBoardAdapter;
import com.bitsaa.bamc.Interface.Participant_Details;
import com.bitsaa.bamc.R;

import java.util.ArrayList;

/**
 * Created by Animesh on 10/19/2016.
 */
public class LeaderBoard extends Fragment {

    ArrayList<Participant_Details> ParticipantArray;
    Participant_Details pd;
    LeaderBoardAdapter ldAdapter;
    public LeaderBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParticipantArray=new ArrayList<Participant_Details>();
        pd=new Participant_Details("Monil","2","200");
        ParticipantArray.add(pd);
        pd=new Participant_Details("Maddy","3","0");
        ParticipantArray.add(pd);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_leader_board, container, false);
       /* recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        ldAdapter =new LeaderBoardAdapter(getContext(),ParticipantArray);
        recyclerView.setAdapter(ldAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/
        return  view;
    }

}
