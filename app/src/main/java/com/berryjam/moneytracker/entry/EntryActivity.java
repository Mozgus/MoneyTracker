package com.berryjam.moneytracker.entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.berryjam.moneytracker.main.MainActivity;
import com.berryjam.moneytracker.R;

public class EntryActivity extends AppCompatActivity {

    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        //TODO Google sign in
        startActivity(new Intent(this, MainActivity.class));
    }

}
