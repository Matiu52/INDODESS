package com.matius.indodess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

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

        banner = (TextView) findViewById(R.id.banner);
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
            case R.id.banner:
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


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Berhasil melakukan register!",
                                                Toast.LENGTH_LONG).show();
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