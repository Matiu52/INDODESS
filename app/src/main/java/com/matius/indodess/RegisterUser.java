package com.matius.indodess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText etusername, etemail, etpassword;
    private Button registerUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.bannerRegister);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        etusername = (EditText) findViewById(R.id.usernameReg);
        etemail = (EditText) findViewById(R.id.emailReg);
        etpassword = (EditText) findViewById(R.id.passwordReg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bannerRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
        }
    }

    private void registerUser() {
        String username = etusername.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if(username.isEmpty()){
            etusername.setError("Masukkan username !");
            etusername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etemail.setError("Masukkan email !");
            etemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Masukkan email yang benar!");
            etemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etpassword.setError("Masukkan password !");
            etpassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            etpassword.setError("Password harus lebih dari 6 karakter !");
            etpassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            float saldo = 999999999;
                            User user = new User(username, email, password, saldo);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Berhasil melakukan register!",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    } else{
                                        Toast.makeText(RegisterUser.this, "Registrasi gagal dilakukan!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else{
                            Toast.makeText(RegisterUser.this, "Registrasi gagal dilakukan!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}