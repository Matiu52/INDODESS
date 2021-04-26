package com.matius.indodess.ui.home;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("users");

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference idRef = mFirebaseDatabase.child(id);

        mText = new MutableLiveData<>();

        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();

                mText.setValue(username + "=" + email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}