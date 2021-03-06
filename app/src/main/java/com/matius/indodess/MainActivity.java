package com.matius.indodess;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText inputUsername, inputPassword;
    private Button btnLogin, btnRegister;
    private  TextView forgotPassword;
    private DatabaseReference mFirebaseDatabase;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        forgotPassword = findViewById(R.id.resetPassword);
        forgotPassword.setOnClickListener(this);

        btnRegister.setOnClickListener(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("INDODESS");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameIn = inputUsername.getText().toString();
                String passwordIn = inputPassword.getText().toString();

                if (TextUtils.isEmpty(usernameIn) ||
                        TextUtils.isEmpty(passwordIn)) {
                    Toast.makeText(MainActivity.this, "Masih ada field yang kosong!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String id = mAuth.getCurrentUser().getUid();
                    DatabaseReference idRef = mFirebaseDatabase.child(id);

                    idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String username = dataSnapshot.child("username").getValue().toString();
                            String password = dataSnapshot.child("password").getValue().toString();
                            String email = dataSnapshot.child("email").getValue().toString();

                            if (usernameIn.equals(username) && passwordIn.equals(password)) {
                                inputUsername.setText("");
                                inputPassword.setText("");
                                Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else if (!usernameIn.equals(username) || !passwordIn.equals(password)) {
                                Toast.makeText(MainActivity.this, "username atau password tidak cocok!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.resetPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }
}
