package edu.gatech.seclass.sdpguessit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

import edu.gatech.seclass.sdpguessit.R;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;

/**
 * Created by tofiques on 3/7/18.
 */

public class PuzzleAdapter extends BaseAdapter {


    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Puzzle> objects;

    PuzzleAdapter (Context context, ArrayList<Puzzle> puzzles) {
        ctx = context;
        objects = puzzles;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.activity_create_tournament, parent, false);
        }
        Puzzle puzzle=getPuzzle(position);
        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.txtTournamnetListCheckBox);
        cbBuy.setTag(position);


        return view;
    }
    Puzzle getPuzzle(int position) {
        return ((Puzzle) getItem(position));
    }

}
