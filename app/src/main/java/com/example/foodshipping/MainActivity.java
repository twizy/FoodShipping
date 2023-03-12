package com.example.foodshipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button profileP, articleP, newarticleP, logoutP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileP = (Button) findViewById(R.id.profilepage);
        articleP = (Button) findViewById(R.id.articlepage);
        newarticleP = (Button) findViewById(R.id.newarticlepage);
        logoutP = (Button) findViewById(R.id.logout);

        profileP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Userprofile.class));
            }
        });

        articleP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Articles.class));
            }
        });

        newarticleP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Newarticle.class));
            }
        });

    }
}