package com.demo.assignment.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.assignment.R;
import com.demo.assignment.adapters.TeamAdapter;
import com.demo.assignment.models.AwayTeam;

public class AwayTeamFragment extends Fragment {

    private AwayTeam awayTeam;
    private RecyclerView awayTealRecyclerView;
    private TextView noDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            awayTeam = (AwayTeam) bundle.getSerializable("away_Team_data");
            Log.e("Away Team Size", awayTeam.getPlayers().size() + "");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pView = inflater.inflate(R.layout.fragment_team, container, false);
        awayTealRecyclerView = pView.findViewById(R.id.teamRecyclerView);
        noDataTextView = pView.findViewById(R.id.noDataTV);
        return pView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        awayTealRecyclerView.setLayoutManager(linearLayoutManager);

        if (awayTeam.getPlayers().size() > 0) {
            setUpAwayTeamData(awayTeam);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
            awayTealRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setUpAwayTeamData(AwayTeam awayTeamData) {

        TeamAdapter teamAdapter = new TeamAdapter(getActivity(), awayTeamData.getPlayers());
        awayTealRecyclerView.setAdapter(teamAdapter);
        awayTealRecyclerView.setVisibility(View.VISIBLE);
    }
}