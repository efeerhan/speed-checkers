package com.crunglers.speedcheckers.firebase;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crunglers.speedcheckers.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class HomeActivity extends AppCompatActivity {

    private String status = "matching";
    private String playerUniqueId;
    private String opponentUniqueId = "0";
    private Boolean opponentFound = false;
    private String getPlayerName;
    private String connectionId = "";
    ValueEventListener turnsEventListener, wonEventListener;
    EditText friendEmail;
    private String getFriendEmail() {return getFriendEmail().replaceAll(".","");}
    Button challengeButton;
    TextView checkRec;
    Button logoutButton;



    /**
     * ------- onCreate() ----------------------------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* Finding the Needed Views */
        friendEmail = findViewById(R.id.friendEmail);
        challengeButton = findViewById(R.id.challengeButton);
        checkRec = findViewById(R.id.checkRec);
        logoutButton = findViewById(R.id.logoutButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        /* Sets Email on Home Screen */
        checkRec.setText(currentUser.getEmail());

        playerUniqueId = String.valueOf(System.currentTimeMillis());
        getPlayerName = getIntent().getStringExtra("player_name");


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("connections").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("jetbug","STARTING SEARCH\n\n");
                if (!opponentFound) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot connections: snapshot.getChildren()) {
                            Log.d("jetbug","\nHERE IS CONNECtion " + connections.getKey()+ "\n");
                            String conID = connections.getKey();
                            int getPlayerCount = (int) connections.getChildrenCount();

                            if (status.equals("waiting")) {
                                if (getPlayerCount == 2) {
                                    Boolean playerFound = false;
                                    //other person joined
                                    for (DataSnapshot players: snapshot.getChildren()) {
                                        String getPlayerUniqueId = players.getKey();
                                        if (getPlayerUniqueId.equals(playerUniqueId)) {
                                            playerFound = true;

                                        }
                                        else if (playerFound) {
                                            String getOpponentPlayerName = players.child("player_name").getValue(String.class);
                                            opponentUniqueId = players.getKey();
                                            opponentFound = true;



                                            db.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
                                            db.child("won").child(connectionId).addValueEventListener(wonEventListener);
                                            db.child("connections").removeEventListener(this);
                                            break;

                                        }
                                    }
                                }
                            }
                            else {
                                if (getPlayerCount == 1) {
                                    connections.child(playerUniqueId).child("plauyer_name").getRef().setValue(getPlayerName);
                                    for (DataSnapshot players : connections.getChildren()) {
                                        String getOpponentsName = players.child("player_name").getValue(String.class);
                                        opponentUniqueId = players.getKey();
                                        Log.d("jetbug","I AM HERE \n\n " + getOpponentsName);
                                        //FIRST TURN GOES TO ROOMMAKER
                                        connectionId = conID;
                                        opponentFound = true;
                                    }
                                }
                            }
                        }
                        if (!opponentFound && status.equals("waiting")) {
                            String connectionUniqueId = String.valueOf(System.currentTimeMillis());
                            snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").getRef().setValue(getPlayerName);
                            status = "waiting";
                        }
                    }
                    else {
                        String connectionUniqueId = String.valueOf(System.currentTimeMillis());
                        snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").getRef().setValue(getPlayerName);
                        status = "waiting";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        turnsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        wonEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };




        /* Pop-up Challenge Stufffff */ // ------------------------ Start --------------------------
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_challenged, null);
        final View popupView2 = inflater.inflate(R.layout.popup_challenging, null);

        TextView backgroundColor = popupView.findViewById(R.id.challengedBackground);
        backgroundColor = popupView2.findViewById(R.id.textView6);

        final PopupWindow challengedWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        challengedWindow.setOutsideTouchable(false);

        final PopupWindow challengingWindow = new PopupWindow(popupView2, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        challengingWindow.setOutsideTouchable(false);

        final TextView challengingText = popupView2.findViewById(R.id.challengingText);
        final TextView challengedText = popupView.findViewById(R.id.challengedText);
        /* Pop-up Challenge Stufffff */ // ------------------------ End --------------------------


        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengingText.setText("Challenging " + friendEmail.getText().toString() + "...");
                challengingWindow.showAtLocation(findViewById(R.id.homeLayout), Gravity.CENTER, 0, 0);
                challengingWindow.getContentView().findViewById(R.id.cancelChallenge).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        challengingWindow.dismiss();
                    }

                });
            }
        });
    }
    /**
     * ------- LogoutButtonClickListener ----------------------------------------------------------------------------------
     */
    private class LogoutButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            final View view = v;

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users/" + currentUser.getEmail().replaceAll(".",""));
            myRef.setValue(null);

            AuthUI.getInstance()
                    .signOut(view.getContext())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(intent);
                        }
                    });
        }
    }
}
