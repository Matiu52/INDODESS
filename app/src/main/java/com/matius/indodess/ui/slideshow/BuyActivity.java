package com.matius.indodess.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matius.indodess.HomeActivity;
import com.matius.indodess.MainActivity;
import com.matius.indodess.R;

import java.util.HashMap;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {

    private EditText editTextBuy;
    private Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        getSupportActionBar().setTitle("BELI");

        editTextBuy = findViewById(R.id.editTextBuy);
        btnBuy = findViewById(R.id.btnBuy);

        Intent intent = getIntent();
        String saldo = intent.getStringExtra("saldo");

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jml = editTextBuy.getText().toString();
                float total = Float.parseFloat(saldo) - Float.parseFloat(jml);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

                DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("users");

                String id = mAuth.getCurrentUser().getUid();
                DatabaseReference idRef = mFirebaseDatabase.child(id);

                idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String username = dataSnapshot.child("username").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        User user = new User(username, password, email, total);

                        idRef.updateChildren();

                        Toast.makeText(BuyActivity.this, "Transaksi berhasil", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BuyActivity.this, SlideshowFragment.class));
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}