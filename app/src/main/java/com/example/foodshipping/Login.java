package com.example.foodshipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText usernameEdx, passwordEdx;
    private Button loginBtn, createBtn;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.login_toolbar);
        toolbar.setTitle("Login");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        usernameEdx = findViewById(R.id.username);
        passwordEdx = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        createBtn = findViewById(R.id.signup_id);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginUser();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

    }

    private void loginUser() {

        String emaU = usernameEdx.getText().toString().trim();
        String passU = passwordEdx.getText().toString().trim();

        if (TextUtils.isEmpty(emaU)) {
            Toast.makeText(this, "Email is empty !", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(passU)) {
            Toast.makeText(this, "Password is empty !", Toast.LENGTH_LONG).show();
        }
        else {

            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Authenticating . . .");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(emaU, passU).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        passwordEdx.setText("");
                        Toast.makeText(Login.this, "Wrong password !", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else if (task.isSuccessful()) {

                        Intent intent = new Intent(Login.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        Toast.makeText(Login.this, "Connected", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            });

        }

    }

}