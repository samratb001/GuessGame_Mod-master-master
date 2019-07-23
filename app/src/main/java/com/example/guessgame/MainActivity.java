
package com.example.guessgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public EditText pname;
    public ImageView img;
    public ImageButton play;
    ImageView dialog;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.playbutton);
        pname = findViewById(R.id.name);
        realm=Realm.getDefaultInstance();
        img=findViewById(R.id.fimage);
        dialog=findViewById(R.id.dialog_main);
        dialog.setVisibility(View.INVISIBLE);
    }
    public void onFade(View view){
        img.animate().alpha(0f).setDuration(2000);
    }
    public void onPlayClick(View view) {
        String p_name = pname.getText().toString();
        if (p_name.equals("") || p_name.equals(" ")) {
            //Toast.makeText(this, "Required Name", Toast.LENGTH_SHORT).show();
            dialog.setVisibility(View.VISIBLE);
            pname.requestFocus();
        } else {
            Intent intent = new Intent(this, GameActivity.class);
            String name = pname.getText().toString();
            intent.putExtra("playerName", name);
            startActivity(intent);
        }
    }
    public void onDisplay(View view) {
        Intent intent=new Intent(this,DisplayActivity.class);
        startActivity(intent);
    }


}
