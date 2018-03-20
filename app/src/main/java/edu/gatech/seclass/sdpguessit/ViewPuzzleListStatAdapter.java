package edu.gatech.seclass.sdpguessit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpguessit.utils.PuzzleStat;

/**
 * Created by kausett on 3/9/18.
 */


public class ViewPuzzleListStatAdapter extends RecyclerView.Adapter<ViewPuzzleListStatAdapter.ViewPuzzleStatViewHolder>{
    private Context mContext;
    private List<PuzzleStat> data;

    public ViewPuzzleListStatAdapter(Context mContext, List<PuzzleStat> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewPuzzleStatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_view_puzzle_list_stats_recycler, null);
        ViewPuzzleStatViewHolder holder = new ViewPuzzleStatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewPuzzleStatViewHolder holder, int position) {
        PuzzleStat ps =  data.get(position);
        holder.textViewUsername.setText(ps.getUsername());
        holder.textViewPuzzleId.setText(String.valueOf(ps.getPuzzleID()));
        holder.textViewPuzzleTopPrize.setText(String.valueOf(ps.getTopScore()));
        holder.txtNoOfPuzzlePlayers.setText(String.valueOf(ps.getNumPlayers()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewPuzzleStatViewHolder extends RecyclerView.ViewHolder{
        TextView textViewUsername, textViewPuzzleId,textViewPuzzleTopPrize,txtNoOfPuzzlePlayers;
        public ViewPuzzleStatViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.txtPzlUserName);
            textViewPuzzleId = itemView.findViewById(R.id.txtPuzzleId);
            textViewPuzzleTopPrize = itemView.findViewById(R.id.txtTopPrice);
            txtNoOfPuzzlePlayers = itemView.findViewById(R.id.txtNoOfPlayers);
        }
    }
}