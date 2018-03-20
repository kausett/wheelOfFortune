package edu.gatech.seclass.sdpguessit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;
import edu.gatech.seclass.sdpguessit.utils.UserTournamentStat;

/**
 * Created by kausett on 3/10/18.
 */

public class ViewTournamentStatsAdapter extends RecyclerView.Adapter<ViewTournamentStatsAdapter.ViewPuzzleStatsViewHolder>{
    private Context mContext;
    private List<UserTournamentStat> data;

    public ViewTournamentStatsAdapter(Context mContext, List<UserTournamentStat> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewPuzzleStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_view_puzzle_stats_recycler, null);
        ViewPuzzleStatsViewHolder holder = new ViewPuzzleStatsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewPuzzleStatsViewHolder holder, int position) {
        UserTournamentStat ups =  data.get(position);
        holder.txtPuzzleId.setText(String.valueOf(ups.getTournamentName()));
        holder.txtPuzzleName.setText(String.valueOf(ups.getScore()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewPuzzleStatsViewHolder extends RecyclerView.ViewHolder{
        TextView txtPuzzleId , txtPuzzleName;
        public ViewPuzzleStatsViewHolder(View itemView) {
            super(itemView);
            txtPuzzleId = itemView.findViewById(R.id.txtPuzzleId);
            txtPuzzleName = itemView.findViewById(R.id.txtUsername);
        }
    }
}
