package com.example.foodshipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Userprofile extends AppCompatActivity {

    private EditText fullname,province,commune,zone,phone,work;
    private Button save;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Profil");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        fullname = (EditText) findViewById(R.id.fullname_id);
        province = (EditText) findViewById(R.id.province_id);
        commune = (EditText) findViewById(R.id.commune_id);
        zone = (EditText) findViewById(R.id.zone_id);
        phone = (EditText) findViewById(R.id.phone_id);
        work = (EditText) findViewById(R.id.work_id);
        save = (Button) findViewById(R.id.save_id);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Demo-Minagris").child("Profiles");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullname.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Tout le nom est vide !", Toast.LENGTH_SHORT).show();
                }
                else if(province.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Province est vide !", Toast.LENGTH_SHORT).show();
                }
                else if(commune.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Commune est vide !", Toast.LENGTH_SHORT).show();
                }
                else if(zone.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Zone est vide !", Toast.LENGTH_SHORT).show();
                }
                else if(phone.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Phone est vide !", Toast.LENGTH_SHORT).show();
                }
                else if(work.getText().toString().isEmpty()){
                    Toast.makeText(Userprofile.this, "Travail est vide !", Toast.LENGTH_SHORT).show();
                }
                else{
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String dat = simpleDateFormat.format(new Date());

                    HashMap<String, Object> newProd = new HashMap<>();
                    newProd.put("fullname", fullname.getText().toString());
                    newProd.put("province", province.getText().toString());
                    newProd.put("commune", commune.getText().toString());
                    newProd.put("zone", zone.getText().toString());
                    newProd.put("phone", phone.getText().toString());

                    String insKey = databaseReference.push().getKey();

                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(newProd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Userprofile.this, "Success !", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Userprofile.this, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(Userprofile.this, "Error, Product is not added !", Toast.LENGTH_LONG).show();
                                System.out.println(task.getException().toString()+ "\n");
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getUid() == null){
            firebaseAuth.signOut();
            finish();
        }
    }
}