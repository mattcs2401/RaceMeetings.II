package com.mcssoft.racemeetings.ii.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.interfaces.IRaceItemClickListener;

public class RacesViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public RacesViewHolder(View view) {
        super(view);
    }

    public RacesViewHolder(View view, IRaceItemClickListener listener) {
        super(view);
        tvRaceNo = (TextView) view.findViewById(R.id.tv_id_race_no);
        tvRaceName = (TextView) view.findViewById(R.id.tv_id_race_name);
        tvRaceTime = (TextView) view.findViewById(R.id.tv_id_race_time);
        tvRaceDist = (TextView) view.findViewById(R.id.tv_id_race_dist);

        itemClickListener = listener;
        view.setOnClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public TextView getTvRaceNo() { return tvRaceNo; }
    public TextView getTvRaceName() { return tvRaceName; }
    public TextView getTvRaceTime() { return tvRaceTime; }
    public TextView getTvRaceDist() { return tvRaceDist; }
    //</editor-fold>

    private TextView tvRaceNo;
    private TextView tvRaceName;
    private TextView tvRaceTime;
    private TextView tvRaceDist;

    private IRaceItemClickListener itemClickListener;
}
