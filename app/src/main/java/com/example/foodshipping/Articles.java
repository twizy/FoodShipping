package com.example.foodshipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Articles extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ArticleClass> list;
    private ArrayAdapter<ArticleClass> adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.article_toolbar);
        toolbar.setTitle("Liste des articles");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        progressDialog = new ProgressDialog(Articles.this);
        progressDialog.setMessage("Patientez...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listview);
    }


    private void allDatas() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Demo-Minagris").child("Products");

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        ArticleClass allClientsClass = dataSnapshot.getValue(ArticleClass.class);
                        list.add(allClientsClass);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }

                ArticleAdapter notFound = new ArticleAdapter(Articles.this, list);
                listView.setAdapter(notFound);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getUid() != null){
            allDatas();
        }
    }
}