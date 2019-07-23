package com.example.guessgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private String playerName;
    ImageButton btn_animal,btn_flower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btn_animal=findViewById(R.id.animal_button);
        btn_flower=findViewById(R.id.flower_button);
//        b1=findViewById(R.id.button1);
//        b2=findViewById(R.id.button2);
//        b3=findViewById(R.id.button3);
//        b4=findViewById(R.id.button4);
//        tv_ma=findViewById(R.id.tv_user_name);
        playerName=getIntent().getStringExtra("playerName");
    }

//    public void onSubmit(View view) {
//        Realm.init(this);
//        String user=tv_ma.getText().toString();
//        realm=Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try{
//            if(realm.isEmpty()){
//                Scores score=realm.createObject(Scores.class,user);
//                score.setScore(5);
//                realm.commitTransaction();
//                Toast.makeText(mContext, "Successful transaction", Toast.LENGTH_SHORT).show();
//                realm.close();
//                return;
//            }
//            Scores res=realm.where(Scores.class).equalTo("userName",user).findFirst();
//            if(res==null){
//                Scores score=realm.createObject(Scores.class,user);
//                score.setScore(5);
//
//            }
//            else{
//                long s=res.getScore();
//                res.setScore(s+5);
//            }
//            realm.commitTransaction();
//            Toast.makeText(mContext, "Successful transaction", Toast.LENGTH_SHORT).show();
//        }
//        catch(Exception e){
//            realm.cancelTransaction();
//            Toast.makeText(mContext, "Failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        realm.close();
//    }
//
//    public void onDisplay(View view) {
//        Intent intent=new Intent(mContext,DisplayActivity.class);
//        startActivity(intent);
//    }

    public void onPress(View view) {

        Intent intent = null;
        if(btn_animal.isPressed()) intent= new Intent(this, AnimalQuizActivity.class);
        if(btn_flower.isPressed()) intent= new Intent(this, FlowerQuizActivity.class);
        intent.putExtra("playerName",playerName);
        startActivity(intent);
    }
}
