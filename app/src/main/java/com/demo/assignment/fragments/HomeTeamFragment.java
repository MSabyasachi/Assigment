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
import com.demo.assignment.models.HomeTeam;

public class HomeTeamFragment extends Fragment {

    private HomeTeam homeTeam;
    private RecyclerView homeTealRecyclerView;
    private TextView noDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            homeTeam = (HomeTeam) bundle.getSerializable("home_Team_data");
            Log.e("Home Team Size", homeTeam.getPlayers().size() + "");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pView = inflater.inflate(R.layout.fragment_team, container, false);

        homeTealRecyclerView = pView.findViewById(R.id.teamRecyclerView);
        noDataTextView = pView.findViewById(R.id.noDataTV);
        return pView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homeTealRecyclerView.setLayoutManager(linearLayoutManager);

        if (homeTeam.getPlayers().size() > 0) {
            setUpHomeTeamData(homeTeam);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
            homeTealRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setUpHomeTeamData(HomeTeam homeTeam) {
        TeamAdapter teamAdapter = new TeamAdapter(getActivity(), homeTeam.getPlayers());
        homeTealRecyclerView.setAdapter(teamAdapter);
        homeTealRecyclerView.setVisibility(View.VISIBLE);
    }
}