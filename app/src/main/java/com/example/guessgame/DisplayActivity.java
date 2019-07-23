package com.example.guessgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class DisplayActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mRecyclerView=findViewById(R.id.scoreRecycler);
        Realm realm= Realm.getDefaultInstance();
        if(realm.isEmpty())
            return;
        RealmResults<Scores> scoresRecord=realm.where(Scores.class).findAll();
        scoresRecord=scoresRecord.sort("score", Sort.DESCENDING);
        MyAdapter myAdapter=new MyAdapter(scoresRecord,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);

    }

    public  void backhome(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
