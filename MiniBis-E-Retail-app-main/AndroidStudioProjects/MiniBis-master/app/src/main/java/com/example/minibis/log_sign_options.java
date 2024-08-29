package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class log_sign_options extends AppCompatActivity {

    Button btncust, btnseller,loginBtn;
    EditText username,password;
    private FirebaseAuth firebaseAuth;
    private LottieAnimationView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_options);

        loading=(LottieAnimationView) findViewById(R.id.loadingAnimationOnLogin);
        loginBtn=(Button) findViewById(R.id.loginButton);
        btncust=(Button) findViewById(R.id.csignup);
        btnseller=(Button)findViewById(R.id.ssignup);
        username=(EditText) findViewById(R.id.login_id);
        password=(EditText) findViewById(R.id.login_pass);

        Intent okIntent = new Intent(log_sign_options.this,MainActivity.class);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Log.d("Info",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

//        Toast.makeText(log_sign_options.this, "Welcome", Toast.LENGTH_LONG).show();
        btncust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), signupcustomer.class);
                startActivity(i1);
                finish();
            }
        });
        btnseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), signupseller1.class);
                startActivity(i2);
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                String uname=username.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(getApplicationContext(),"Error: Username cannot be Empty",Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(log_sign_options.this, "Error: Password cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else{
                    firebaseAuth = FirebaseAuth.getInstance();
                    if(firebaseAuth.getCurrentUser()==null){
                        firebaseAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(log_sign_options.this, "Sign in Success. Redirecting", Toast.LENGTH_SHORT).show();
                                    Log.d("","Login Successful UID: "+firebaseAuth.getCurrentUser().getEmail());
                                    finishAffinity();
                                    startActivity(okIntent);
                                }
                                else{
                                    Log.w("","Login Failure: ", task.getException());
                                    loading.setVisibility(View.INVISIBLE);
                                    password.setText("");
                                    Toast.makeText(log_sign_options.this, "Login Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}