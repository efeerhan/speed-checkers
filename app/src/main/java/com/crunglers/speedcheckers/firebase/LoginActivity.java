package com.crunglers.speedcheckers.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.crunglers.speedcheckers.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;




public class LoginActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";
    private ActivityResultLauncher<Intent> signInLauncher = null;

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent( this, HomeActivity.class );
            intent.putExtra("player_name",user.getDisplayName());
            startActivity( intent );

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d( DEBUG_TAG, "MainActivity.onCreate()" );

        Button signInButton = findViewById( R.id.button1 );

        signInButton.setOnClickListener( new SignInButtonClickListener() );
        signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> onSignInResult(result)
        );

        // Check if user is signed in and if signed in, sign the user out before proceeding.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if( currentUser != null )
            mAuth.signOut();
    }

    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {

            // This is an example of how to use the AuthUI activity for signing in
            // Here, we are just using email/password sign in
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch(signInIntent);
        }
    }
}