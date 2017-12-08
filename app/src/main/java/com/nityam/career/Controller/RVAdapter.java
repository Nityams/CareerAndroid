package com.nityam.career.Controller;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nityam.career.Model.JobPost;
import com.nityam.career.R;

import java.util.List;


/**
 * Created by nityamshrestha on 12/4/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder>{
    List<JobPost> datas;

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView company;
        TextView position;
        TextView date;
        TextView location;
        Button status;

        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            company = (TextView) itemView.findViewById(R.id.cardCompany);
            position = (TextView) itemView.findViewById(R.id.cardPosition);
            date = (TextView) itemView.findViewById(R.id.cardDate);
            location = (TextView) itemView.findViewById(R.id.cardLocation);
            status = (Button) itemView.findViewById(R.id.cardStatus);
        }
    }

   public RVAdapter(List<JobPost> datas){

       this.datas = datas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards, viewGroup, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder DataViewHolder, int i) {
        DataViewHolder.company.setText(datas.get(i).getCompany());
        DataViewHolder.position.setText(datas.get(i).getPosition());
        DataViewHolder.date.setText(datas.get(i).getDate());
        DataViewHolder.location.setText(datas.get(i).getCity());
        String st = datas.get(i).getStatus();

//        DataViewHolder.status.setBackgroundColor(Color.BLUE);


        switch(st){
            case "interview" : DataViewHolder.status.setBackgroundColor(Color.YELLOW);
                                break;
            case "offered" :   DataViewHolder.status.setBackgroundColor(Color.GREEN);
                                break;
            case "rejected" :   DataViewHolder.status.setBackgroundColor(Color.RED);
                                break;
            case "will apply": DataViewHolder.status.setBackgroundColor(Color.CYAN);
                                break;
            default:
                                DataViewHolder.status.setBackgroundColor(Color.BLUE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


}