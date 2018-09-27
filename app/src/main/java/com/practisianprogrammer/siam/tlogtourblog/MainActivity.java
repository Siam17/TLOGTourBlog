package com.practisianprogrammer.siam.tlogtourblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private android.support.v7.widget.Toolbar mainToolbar;

    private  FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.main_toolbar);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("TLOG");

        fAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_manue, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout_btn:

                logOut();

                return true;
            case R.id.action_setttings_btn:
                sendToAccount();
            default:
                return false;
        }


    }

    private void sendToAccount() {
        Intent accountIntent= new Intent(MainActivity.this, AccountActivity.class);

        startActivity(accountIntent);
        overridePendingTransition(R.anim.right, R.anim.right );
        finish();
    }

    private void logOut() {
        fAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {
        Intent loginIntent= new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
