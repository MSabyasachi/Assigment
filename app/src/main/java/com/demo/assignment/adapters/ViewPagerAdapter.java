package com.demo.assignment.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.assignment.fragments.AwayTeamFragment;
import com.demo.assignment.fragments.HomeTeamFragment;
import com.demo.assignment.models.PlayerInfoModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private PlayerInfoModel playerInfoModel;

    public ViewPagerAdapter(FragmentManager fragmentManager, PlayerInfoModel playerInfoModel) {

        super(fragmentManager);
        this.playerInfoModel = playerInfoModel;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new HomeTeamFragment();
            Bundle homeTeamBundle = new Bundle();
            homeTeamBundle.putSerializable("home_Team_data", playerInfoModel.getLineups().getData().getHomeTeam());
            fragment.setArguments(homeTeamBundle);
        } else if (position == 1) {
            fragment = new AwayTeamFragment();
            Bundle awayTeamBundle = new Bundle();
            awayTeamBundle.putSerializable("away_Team_data", playerInfoModel.getLineups().getData().getAwayTeam());
            fragment.setArguments(awayTeamBundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Home Team";
        } else if (position == 1) {
            title = "Away Team";
        }
        return title;
    }
}