package com.example.guessgame;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import pl.droidsonroids.gif.GifImageView;

public class FlowerQuizActivity extends AppCompatActivity {

    Dialog myDialog;
    TextView tv_ma,main_name,timer_tv,d_name,d_score;
    Context mContext;
    ImageButton aib;
    private String playerName;
    String option="";
    String[] imagename={"lily","orchid","jasmine","begonia","rose"};
    Set<Integer> s;
    Set<Integer> sb;
    Button next,b1,b2,b3,b4;
    int wrongCount;
    int id,last_indication,imglength=imagename.length;
    Realm realm;
    View v;
    CountDownTimer clock;
    GifImageView gif;
    private int click_count=0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.flower_display);
        aib=findViewById(R.id.pic);
        realm=Realm.getDefaultInstance();
        next=findViewById(R.id.next);
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        gif=findViewById(R.id.gifImageView);
        timer_tv=findViewById(R.id.timer);
        main_name=findViewById(R.id.name);
        wrongCount=0;
        playerName=getIntent().getStringExtra("playerName");
        tv_ma=findViewById(R.id.tv_user_name);
        tv_ma.setText(playerName);
//        b1.setBackgroundColor(R.attr.colorButtonNormal);
//        b2.setBackgroundColor(R.attr.colorButtonNormal);
//        b3.setBackgroundColor(R.attr.colorButtonNormal);
//        b4.setBackgroundColor(R.attr.colorButtonNormal);
        b1.setBackgroundResource(R.drawable.button_shape);
        b2.setBackgroundResource(R.drawable.button_shape);
        b3.setBackgroundResource(R.drawable.button_shape);
        b4.setBackgroundResource(R.drawable.button_shape);
        next.setVisibility(View.INVISIBLE);
        s=new <Integer> HashSet();
        sb=new <Integer> HashSet();
        double randomDouble = Math.random();
        randomDouble = randomDouble * 5;
        int randomInt = (int) randomDouble;
        id = getResources().getIdentifier(imagename[randomInt], "drawable", getPackageName());
        s.add(randomInt);
        aib.setImageResource(id);
        aib.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int rb;
        double rd;
        rd = Math.random()*4;
        rb = (int) rd;int flag=rb;
        option=imagename[randomInt];
        if(rb==0){
            b1.setText(imagename[randomInt]);
        }

        if(rb==1)
        {
            b2.setText(imagename[randomInt]);
        }

        if(rb==2){
            b3.setText(imagename[randomInt]);
        }
        if(rb==3){
            b4.setText(imagename[randomInt]);
        }

        int temp=rb;
        while(sb.size()<3)
        {
            rd = Math.random() * 4;
            rb = (int) rd;
            if(sb.contains(rb)||rb==randomInt)
                continue;
            else {
                sb.add(rb);

                temp = rb;
            }
        }
        Log.d("ADebugTag", "Value: " + sb.toString());
        int a[]=new int[3];int j=0;
        for(int i:sb)
        {
            a[j++] =i;
        }
        if(flag==0)
        {
            b2.setText(imagename[a[0]]);
            b3.setText(imagename[a[1]]);
            b4.setText(imagename[a[2]]);
        }
        else if(flag==1)
        {
            b1.setText(imagename[a[0]]);
            b3.setText(imagename[a[1]]);
            b4.setText(imagename[a[2]]);
        }
        else if(flag==2)
        {
            b2.setText(imagename[a[0]]);
            b1.setText(imagename[a[1]]);
            b4.setText(imagename[a[2]]);
        }
        else
        {
            b2.setText(imagename[a[0]]);
            b3.setText(imagename[a[1]]);
            b1.setText(imagename[a[2]]);
        }
        mContext=this;
        //Timer will start from here
        clock=new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                timer_tv.setText(String.format("%ds", l / 1000));
            }
            @Override
            public void onFinish() {
                next(v);
            }
        }.start();
    }
    public void onSubmit(View view) {

        next.setVisibility(View.VISIBLE);
        Button btn=(Button)view;
        if(option.equals(btn.getText().toString())){
            String user=tv_ma.getText().toString();
            btn.setBackgroundColor(Color.GREEN);
            clock.cancel();
            gif.setVisibility(View.INVISIBLE);
            timer_tv.setVisibility(View.INVISIBLE);
            realm.beginTransaction();
            try{
                if(realm.isEmpty()){
                    Scores score=realm.createObject(Scores.class,user);
                    score.setScore(5);
                    realm.commitTransaction();
                    Toast.makeText(mContext, "Correct", Toast.LENGTH_SHORT).show();
                    realm.close();
                    return;
                }
                Scores res=realm.where(Scores.class).equalTo("userName",user).findFirst();
                if(res==null){
                    Scores score=realm.createObject(Scores.class,user);
                    score.setScore(5);

                }
                else{
                    long s=res.getScore();
                    res.setScore(s+5);
                }
                realm.commitTransaction();
                //Toast.makeText(mContext, "Successful transaction", Toast.LENGTH_SHORT).show();
                clock.cancel();
                gif.setVisibility(View.INVISIBLE);
                timer_tv.setVisibility(View.INVISIBLE);

            }
            catch(Exception e){
                realm.cancelTransaction();
                Toast.makeText(mContext, "Failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else {
            btn.setBackgroundColor(Color.RED);
            clock.cancel();
            gif.setVisibility(View.INVISIBLE);
            timer_tv.setVisibility(View.INVISIBLE);
            wrongCount++;
            if(wrongCount==3){
                myDialog=new Dialog(this);
                myDialog.setContentView(R.layout.loser_view);
                myDialog.show();
                d_name=myDialog.findViewById(R.id.dialog_name);
                d_score=myDialog.findViewById(R.id.dialog_score);
                Scores scObject=realm.where(Scores.class).equalTo("userName",playerName).findFirst();
                if(scObject!=null){
                    d_name.setText("Name: "+scObject.getUserName());
                    d_score.setText("Score: "+String.valueOf(scObject.getScore()));
                }
                else{
                    d_name.setText("Name: "+playerName);
                    d_score.setText("Score: 0");
                }
            }
        }
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
        clock.cancel();
    }

    public void next(View view) {

        click_count+=1;

        if(click_count==imagename.length){
            next.setVisibility(View.VISIBLE);
            next.setClickable(true);
            myDialog=new Dialog(this);
            myDialog.setContentView(R.layout.dialog_view);
            myDialog.show();
            d_name=myDialog.findViewById(R.id.dialog_name);
            d_score=myDialog.findViewById(R.id.dialog_score);
            Scores scObject=realm.where(Scores.class).equalTo("userName",playerName).findFirst();
            if(scObject!=null){
                d_name.setText("Name: "+scObject.getUserName());
                d_score.setText("Score: "+String.valueOf(scObject.getScore()));
            }
            else{
                d_name.setText("Name: "+playerName);
                d_score.setText("Score: 0");
            }
            //this is the portion of the dialog name and score
        }
        else {
            timer_tv.setVisibility(View.VISIBLE);
            gif.setVisibility(View.VISIBLE);
            next.setVisibility(View.INVISIBLE);
            b1.setClickable(true);
            b2.setClickable(true);
            b3.setClickable(true);
            b4.setClickable(true);
            b1.setText("");
            b2.setText("");
            b3.setText("");
            b4.setText("");
            sb.removeAll(sb);
            b1.setBackgroundResource(R.drawable.button_shape);
            b2.setBackgroundResource(R.drawable.button_shape);
            b3.setBackgroundResource(R.drawable.button_shape);
            b4.setBackgroundResource(R.drawable.button_shape);
//            b1.setBackgroundColor(R.attr.colorButtonNormal);
//            b2.setBackgroundColor(R.attr.colorButtonNormal);
//            b3.setBackgroundColor(R.attr.colorButtonNormal);
//            b4.setBackgroundColor(R.attr.colorButtonNormal);
            int randomInt;
            double randomDouble;
            randomDouble = Math.random();
            randomDouble = randomDouble * 5;
            randomInt = (int) randomDouble;
            while (s.contains(randomInt)) {
                randomDouble = Math.random();
                randomDouble = randomDouble * 5;
                randomInt = (int) randomDouble;
            }
            id = getResources().getIdentifier(imagename[randomInt], "drawable", getPackageName());
            s.add(randomInt);
            option = imagename[randomInt];
            aib.setImageResource(id);
            aib.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int rb;
            double rd;
            rd = Math.random() * 4;
            rb = (int) rd;
            int flag = rb;
            if (rb == 0)
                b1.setText(imagename[randomInt]);
            else if (rb == 1)
                b2.setText(imagename[randomInt]);
            else if (rb == 2)
                b3.setText(imagename[randomInt]);
            else
                b4.setText(imagename[randomInt]);
            int temp = rb;
            while (sb.size() < 3) {
                rd = Math.random() * 4;
                rb = (int) rd;
                if (rb == temp || rb == randomInt)
                    continue;
                else {
                    sb.add(rb);

                    temp = rb;
                }
            }
            Log.d("ADebugTag", "Value: " + sb.toString());
            int a[] = new int[3];
            int j = 0;
            for (int i : sb) {
                a[j++] = i;
            }
            if (flag == 0) {
                b2.setText(imagename[a[0]]);
                b3.setText(imagename[a[1]]);
                b4.setText(imagename[a[2]]);
            } else if (flag == 1) {
                b1.setText(imagename[a[0]]);
                b3.setText(imagename[a[1]]);
                b4.setText(imagename[a[2]]);
            } else if (flag == 2) {
                b2.setText(imagename[a[0]]);
                b1.setText(imagename[a[1]]);
                b4.setText(imagename[a[2]]);
            } else {
                b2.setText(imagename[a[0]]);
                b3.setText(imagename[a[1]]);
                b1.setText(imagename[a[2]]);
            }
//            if(s.size()==imagename.length)
//            {
//                next.setVisibility(View.INVISIBLE);
//                last_indication=1;
//                Toast.makeText(mContext,"here",Toast.LENGTH_SHORT);
//            }
            clock.start();
        }
    }


    public  void backhome(View view){

        realm.close();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
