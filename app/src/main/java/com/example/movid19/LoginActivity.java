package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.perfmark.Tag;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText email;
    private EditText password;
    private TextView linkSignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button = findViewById(R.id.signin_btn_signin);
        email = findViewById(R.id.signin_edit_email);
        password = findViewById(R.id.signin_edit_password);
        linkSignUp = findViewById(R.id.signin_text_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        final SignUpActivity signUpActivity = new SignUpActivity();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signUpActivity.checkField(email) && signUpActivity.checkField(password)) {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    checkUserAccessLevel(authResult.getUser().getUid());
                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signUpActivity, "Password or Email is incorrect" + e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        final DocumentReference documentReference = firestore.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if (documentSnapshot.getString("isAdmin").equals("1")) {
                    // user is admin
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                    finish();
                }
                else if (documentSnapshot.getString("isManager").equals("1")) {
                    if (documentSnapshot.getString("isActive").equals("0")) {
                        Toast.makeText(LoginActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(),ManagerMainActivity.class));
                        finish();
                    }
                }
                else if (documentSnapshot.getString("isUser").equals("1")) {
                    if (documentSnapshot.getString("isActive").equals("0")) {
                        Toast.makeText(LoginActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(),MainUserActivity.class));
                        finish();
                    }
                }
            }
        });
    }



}