package com.berryjam.moneytracker.entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.berryjam.moneytracker.domain.Api;
import com.berryjam.moneytracker.App;
import com.berryjam.moneytracker.R;
import com.berryjam.moneytracker.domain.AuthResult;
import com.berryjam.moneytracker.main.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 20;

    private Api api;
    private App app;
    private GoogleSignInClient mGoogleSignInClient;

    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        app = ((App) getApplication());
        api = app.getApi();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, options);

        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignIn(data);
        }
    }

    private void handleSignIn(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (result.isSuccess() && result.getSignInAccount() != null) {
            GoogleSignInAccount account = result.getSignInAccount();
            Call<AuthResult> call = api.auth(account.getId());
            call.enqueue(new Callback<AuthResult>() {
                @Override
                public void onResponse(@NonNull Call<AuthResult> call, @NonNull Response<AuthResult> response) {
                    AuthResult authResult = response.body();
                    if (authResult != null) {
                        app.saveAuthToken(authResult.getAuthToken());
                        Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthResult> call, @NonNull Throwable t) {
                    showError(getString(R.string.error_msg_auth));
                }
            });

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
