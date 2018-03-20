package edu.gatech.seclass.sdpguessit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yanqun on 2018/3/5.
 */

public class UserStatAdapter extends RecyclerView.Adapter<UserStatAdapter.ViewHolder> {
    private ArrayList<String> mydata;

    public UserStatAdapter(ArrayList<String> data) {
        this.mydata = data;
    }

    @Override
    public UserStatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_puzzle_by_user, parent, false);//find the view in another layout which is the layout for storing individual card information
        ViewHolder viewHoler = new ViewHolder(v);//create a viewHoler object with this view,this view is actually like a whole activity UI view, and the created viewHoler object know its target view in this UI view(see the constructor)
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(UserStatAdapter.ViewHolder holder, int position) { //connect the data with view variable in the view holder
        holder.singleText.setText(mydata.get(position));//mydata.get(position) will return the single object in the arraylist, which is a string
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //extract the single item xml 's textview id and assign it to a variable
        public TextView singleText;
        public ViewHolder(View itemView) {
            super(itemView);
            singleText = (TextView)itemView.findViewById(R.id.puzzleStat);
        }
    }
}
