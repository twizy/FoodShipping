package com.example.foodshipping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
public class Register extends AppCompatActivity {

    private EditText usernameEdx, passwordEdx;
    private Button signupBtn, loginBtn;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private DatabaseReference rootRef;
    private FirebaseUser user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        toolbar.setTitle("Sign up");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        usernameEdx = (EditText) findViewById(R.id.username);
        passwordEdx = (EditText) findViewById(R.id.password);
        signupBtn = (Button) findViewById(R.id.signup);
        loginBtn = (Button) findViewById(R.id.login_id);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signupUser();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();
            }
        });

    }

    private void signupUser() {

        if(!isNetworkTurnedOn(this)){
            Toast.makeText(this, "Internet is unavailable !", Toast.LENGTH_SHORT).show();
        }
        else{

            String emaU = usernameEdx.getText().toString().trim();
            String passU = passwordEdx.getText().toString().trim();

            if (TextUtils.isEmpty(emaU)) {
                Toast.makeText(this, "Email address is empty !", Toast.LENGTH_LONG).show();
            }
            else if (TextUtils.isEmpty(passU)) {
                Toast.makeText(this, "Password is empty !", Toast.LENGTH_LONG).show();
            }
            else {

                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Authenticating . . .");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                auth.createUserWithEmailAndPassword(emaU, passU).addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            passwordEdx.setText("");
                            Toast.makeText(Register.this, "Wrong password !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else if (task.isSuccessful()) {

                            Intent intent = new Intent(Register.this,ProductsList.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(Register.this, "Connected", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });

            }

        }
    }

    private boolean isNetworkTurnedOn(Register login) {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())){
            return true;
        }else {
            return false;
        }
    }
}