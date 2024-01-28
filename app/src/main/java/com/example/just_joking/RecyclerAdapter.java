package com.example.just_joking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<JokeTemplate> likedJokes;
    Context context;

    public RecyclerAdapter(List<JokeTemplate> likedJokes, Context context) {
        this.likedJokes = likedJokes;
        this.context = context;
    }

    public void setLikedJokes(List<JokeTemplate> likedJokes) {
        this.likedJokes = likedJokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_layout_likedjokes,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.joketype.setText(likedJokes.get(position).getType());
            holder.jokeData.setText(likedJokes.get(position).getJoke());
    }

    @Override
    public int getItemCount() {
        return likedJokes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView joketype;
        private TextView jokeData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            joketype=itemView.findViewById(R.id.jokecategory);
            jokeData=itemView.findViewById(R.id.jokedata);
        }
    }
}
