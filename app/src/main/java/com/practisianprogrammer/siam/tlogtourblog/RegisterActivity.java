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

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmail,regPassword,regConfirmPassword;
    private Button registerButton,goToLoginButton;
    private ProgressBar regProgress;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail= (EditText) findViewById(R.id.reg_email);
        regPassword=(EditText) findViewById(R.id.reg_password);
        regConfirmPassword= (EditText) findViewById(R.id.reg_confirm_password);
        registerButton = (Button) findViewById(R.id.login_btn);
        goToLoginButton= (Button) findViewById(R.id.reg_page_login_btn);
        regProgress =(ProgressBar) findViewById(R.id.reg_progress_bar);
        regProgress.setVisibility(View.INVISIBLE);

        fAuth=FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String confirmedPass= regConfirmPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmedPass)){
                    if(password.equals(confirmedPass)){

                        regProgress.setVisibility(View.VISIBLE);

                        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    sendToAccount();

                                }else{
                                    String errorMsg = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this,"Error :"+errorMsg,Toast.LENGTH_LONG).show();

                                }

                                regProgress.setVisibility(View.INVISIBLE);

                            }
                        });

                    }else{

                        Toast.makeText(RegisterActivity.this, "Password doesn't match with confirm password",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this,"One or more field is empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
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

    private void sendToMain() {
        Intent mainIntent= new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToAccount() {
        Intent accountIntent= new Intent(RegisterActivity.this, AccountActivity.class);
        startActivity(accountIntent);
        finish();
    }
    private void sendToLogin(){
        Intent loginIntent= new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    
}
