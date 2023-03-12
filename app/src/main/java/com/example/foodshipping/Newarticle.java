package com.example.foodshipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;

public class Newarticle extends AppCompatActivity {

    private EditText productname,productprice,productunit;
    private Button save;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newarticle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.newarticle_toolbar);
        toolbar.setTitle("Ajout d'article");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        productname = (EditText) findViewById(R.id.fullname_id);
        productunit = (EditText) findViewById(R.id.province_id);
        productprice = (EditText) findViewById(R.id.commune_id);
        save = (Button) findViewById(R.id.add_now_id);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Demo-Minagris").child("Products");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productname.getText().toString().isEmpty()){
                    Toast.makeText(Newarticle.this, "Product name is empty !", Toast.LENGTH_SHORT).show();
                }
                else if(productunit.getText().toString().isEmpty()){
                    Toast.makeText(Newarticle.this, "Product price is empty !", Toast.LENGTH_SHORT).show();
                }
                else if(productprice.getText().toString().isEmpty()){
                    Toast.makeText(Newarticle.this, "Responsible name is empty !", Toast.LENGTH_SHORT).show();
                }
                else{

                    HashMap<String, Object> newProd = new HashMap<>();
                    newProd.put("productname", productname.getText().toString());
                    newProd.put("productunit", productunit.getText().toString());
                    newProd.put("productprice", productprice.getText().toString());

                    String insKey = databaseReference.push().getKey();

                    newProd.put("productKey", insKey);

                    databaseReference.child(insKey).updateChildren(newProd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Newarticle.this, "Succès !", Toast.LENGTH_LONG).show();
                                productname.setText("");productunit.setText("");productprice.setText("");
                            }else {
                                Toast.makeText(Newarticle.this, "Erreur !", Toast.LENGTH_LONG).show();
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