package com.practisianprogrammer.siam.tlogtourblog;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button loginButton;
    private Button signUpButton;

    private ProgressBar loginProgressBar;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailText = (EditText) findViewById(R.id.reg_email);
        loginPassText = (EditText) findViewById(R.id.reg_confirm_password);
        loginButton = (Button) findViewById(R.id.login_btn);
        signUpButton = (Button) findViewById(R.id.buttonRegister);
        loginProgressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        loginProgressBar.setVisibility(View.INVISIBLE);


        fAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String loginEmail=loginEmailText.getText().toString();
                String loginPassword = loginPassText.getText().toString();
                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)){
                    loginProgressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendToMain();
                            }
                            else{
                                String errorMsg = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error : "+errorMsg, Toast.LENGTH_SHORT).show();
                            }

                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(hasConnection()==false){
                        Toast.makeText(LoginActivity.this,"No internet",Toast.LENGTH_LONG).show();
                }*/


                Intent regIntent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if(currentUser != null){
            sendToMain();

        }
    }
    private void sendToMain(){
        Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    protected boolean hasConnection() {
        try
        {
            //do some stuff here
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }

        }
        catch(Exception e)
        {
            Log.d("EXCEPTION: " ,  e.getMessage());
        }
        return false;


    }
}
