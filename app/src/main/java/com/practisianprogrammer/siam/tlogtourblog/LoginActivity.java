package com.practisianprogrammer.siam.tlogtourblog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        loginEmailText = (EditText) findViewById(R.id.loginEmail);
        loginPassText = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        signUpButton = (Button) findViewById(R.id.buttonSignUp);
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
}
