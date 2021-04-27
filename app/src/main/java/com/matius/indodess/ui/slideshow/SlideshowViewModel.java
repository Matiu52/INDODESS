package com.matius.indodess.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("users");

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference idRef = mFirebaseDatabase.child(id);

        mText = new MutableLiveData<>();

        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp. ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                String saldo = dataSnapshot.child("saldo").getValue().toString();
                float format = Float.parseFloat(saldo);

                mText.setValue(kursIndonesia.format(format) + "=" + saldo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}