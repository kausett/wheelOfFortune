package edu.gatech.seclass.sdpguessit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpguessit.utils.TournamentStat;

/**
 * Created by kausett on 3/9/18.
 */

public class ViewTournamentStatAdapter extends RecyclerView.Adapter<ViewTournamentStatAdapter.ViewTournamentStatViewHolder> {
    private Context mContext;
    private List<TournamentStat> data;

    public ViewTournamentStatAdapter(Context mContext, List<TournamentStat> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewTournamentStatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_view_alltournamnt_stats, null);
        ViewTournamentStatViewHolder holder = new ViewTournamentStatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewTournamentStatViewHolder holder, int position) {
        TournamentStat ts = data.get(position);
        holder.textViewUsername.setText(ts.getUsername());
        holder.textViewPuzzleId.setText(String.valueOf(ts.getTournamentName()));
        holder.textViewPuzzleTopPrize.setText(String.valueOf(ts.getTopScore()));
        holder.txtNoOfPuzzlePlayers.setText(String.valueOf(ts.getNumPlayer()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewTournamentStatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername, textViewPuzzleId, textViewPuzzleTopPrize, txtNoOfPuzzlePlayers;
        public ViewTournamentStatViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.txtPzlUserName);
            textViewPuzzleId = itemView.findViewById(R.id.txtPuzzleId);
            textViewPuzzleTopPrize = itemView.findViewById(R.id.txtTopPrice);
            txtNoOfPuzzlePlayers = itemView.findViewById(R.id.txtNoOfPlayers);
        }
    }
}