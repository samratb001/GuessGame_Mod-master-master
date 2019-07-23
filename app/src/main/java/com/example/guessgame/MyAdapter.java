package com.example.guessgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmResults;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private RealmResults<Scores> scoreRecords;
    private Context mContext;
    private int count;

    public MyAdapter(RealmResults<Scores> scores, Context context) {
        scoreRecords=scores;
        mContext=context;
        count=0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Scores score=scoreRecords.get(position);
        assert score!=null;
        holder.userid.setText(score.getUserName());
        holder.score.setText(String.valueOf(score.getScore()));
        //holder.lm.setBackgroundColor(Color.parseColor("#4CAF50"));
    }

    @Override
    public int getItemCount(){
        return scoreRecords.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView userid,score;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            count++;
            //lm=itemView.findViewById(R.id.rootView);
            userid=itemView.findViewById(R.id.userid);
            score=itemView.findViewById(R.id.score);
        }
    }
}
