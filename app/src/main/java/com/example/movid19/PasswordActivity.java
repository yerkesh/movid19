package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity {
    private EditText password, confirm;
    private Button signup;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        password = findViewById(R.id.password_edit_new);
        confirm = findViewById(R.id.password_edit_conf);
        signup = findViewById(R.id.password_btn_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String nameUser = intent.getStringExtra("Name");
                String surnameUser = intent.getStringExtra("Surname");
                String emailUser = intent.getStringExtra("Email");
                String phoneUser = intent.getStringExtra("Phone");
                if (password.getText().toString().equals(confirm.getText().toString()) && !password.getText().toString().isEmpty()) {
                    // start registration
                    createUser(emailUser,password.getText().toString(),nameUser,surnameUser,phoneUser);
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void createUser(final String emailUser, final String passwordUser, final String nameUser, final String
                           surnameUser, final String phoneUser) {
        firebaseAuth.createUserWithEmailAndPassword(emailUser,passwordUser).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Toast.makeText(PasswordActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                DocumentReference documentReference = firestore.collection("Users").document(user.getUid());
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("Name", nameUser);
                userInfo.put("Surname", surnameUser);
                userInfo.put("Email", emailUser);
                userInfo.put("PhoneNumber", phoneUser);
                userInfo.put("Password", passwordUser);
                //System.out.println(password.getText().toString());
                // If user is admin or manager
                userInfo.put("isAdmin","0");
                userInfo.put("isManager","0");
                userInfo.put("isUser","1");
                documentReference.set(userInfo);

                startActivity(new Intent(getApplicationContext(),MainUserActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}