package com.demo.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.assignment.R;
import com.demo.assignment.models.Player;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private Context context;
    private List<Player> playerList;

    public TeamAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView playerNameTextView, playerRoleTextView;

        private ViewHolder(View v) {
            super(v);
            playerNameTextView = v.findViewById(R.id.playerNameTv);
            playerRoleTextView = v.findViewById(R.id.playerRoleTv);
        }
    }

    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_team, parent, false);
        return new TeamAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeamAdapter.ViewHolder holder, final int position) {
        holder.playerNameTextView.setText("Player Name : " + playerList.get(position).getName());
        holder.playerRoleTextView.setText("Role : " + playerList.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}