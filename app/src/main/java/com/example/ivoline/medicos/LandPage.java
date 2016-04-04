package com.example.ivoline.medicos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LandPage extends AppCompatActivity {

    ImageButton bdrug;
    ImageButton brange;
    private Toolbar mToolbar;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = this.getIntent().getExtras();
        final String Username = extras.getString("USERNAME");
        final int userid = extras.getInt("USERID");

        bdrug = (ImageButton) findViewById(R.id.bdrug);

        bdrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DrugList.class);
                intent.putExtra("DISPLAY","0");
                intent.putExtra("USERNAME",Username);
                intent.putExtra("USERID",userid);
                startActivity(intent);
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });


        brange = (ImageButton) findViewById(R.id.brange);
        brange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DrugList.class);
                intent.putExtra("DISPLAY","1");
                intent.putExtra("USERNAME",Username);
                startActivity(intent);
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
	}
}
