package com.example.foodshipping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button profileP, articleP, newarticleP, logoutP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Profiles");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        profileP = (Button) findViewById(R.id.profilepage);
        articleP = (Button) findViewById(R.id.articlepage);
        newarticleP = (Button) findViewById(R.id.newarticlepage);
        logoutP = (Button) findViewById(R.id.logout);

        firebaseAuth = FirebaseAuth.getInstance();

        profileP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Userprofile.class);
                startActivity(intent);
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

        logoutP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();

            }
        });

    }
}