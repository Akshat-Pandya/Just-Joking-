package com.example.just_joking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LikedJokes extends AppCompatActivity {

    public static List<JokeTemplate> likedJokeList=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_jokes);
        recyclerView=findViewById(R.id.recycler);
        getSupportActionBar().hide();
        RecyclerAdapter adapter=new RecyclerAdapter(likedJokeList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}