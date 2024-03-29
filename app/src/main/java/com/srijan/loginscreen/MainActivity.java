package com.srijan.loginscreen;

import androidx.annotation.MainThread;
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

import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private TextView mTextView;
    private Button signUpBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEmail = findViewById(R.id.email_regn);
        mPass = findViewById(R.id.pass_regn);
        signUpBtn = findViewById(R.id.signup);
        mTextView = findViewById(R.id.already_regn);

        mAuth = FirebaseAuth.getInstance();

        mTextView.setOnClickListener((v) ->{

            Intent intent = new Intent(MainActivity.this, SignInActivity.class);

            startActivity(intent);
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();

            }
        });
    }

    private void createUser() {

        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            if (!pass.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, pass).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this, "Registration Error ! ", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {

                mPass.setError("Empty Filed Are Allowed");
            }
        } else if (email.isEmpty()) {

            mEmail.setError("Empty Filed are not Allowed");
        } else {
            mEmail.setError("Please Enter corroect Email");
        }

    }
}






