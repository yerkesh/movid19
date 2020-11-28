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

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {
    private EditText name, surname, email, password, phone, confirmPassword;
    private Button add_button;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        name = findViewById(R.id.user_add_name);
        surname = findViewById(R.id.user_add_surname);
        email = findViewById(R.id.user_add_email);
        phone = findViewById(R.id.user_add_phone);
        password = findViewById(R.id.user_add_password);
        confirmPassword = findViewById(R.id.user_add_password_conf);

        add_button = findViewById(R.id.user_add_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        final String userAccess = intent.getStringExtra("option");
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameUser = name.getText().toString();
                final String surnameUser = surname.getText().toString();
                final String emailUser = email.getText().toString();
                final String phoneUser = phone.getText().toString();
                final String passwordUser = password.getText().toString();
                String confirmUserWord = confirmPassword.getText().toString();
                System.out.println(nameUser+"  gg"+surnameUser);
                if (!passwordUser.isEmpty() && passwordUser.equals(confirmUserWord)) {
                    firebaseAuth.createUserWithEmailAndPassword(emailUser,passwordUser).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = firestore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Name", nameUser);
                            userInfo.put("Surname", surnameUser);
                            userInfo.put("Email", emailUser);
                            userInfo.put("PhoneNumber", phoneUser);
                            userInfo.put("Password", passwordUser);
//                          System.out.println(password.getText().toString());
//                          If user is admin or manager
                            userInfo.put("isAdmin","0");
                            userInfo.put("isActive","1");
                            if (userAccess.equals("0")) {
                                userInfo.put("isUser","1");
                                userInfo.put("isManager","0");
                            }
                            else if(userAccess.equals("1")) {
                                userInfo.put("isManager","1");
                                userInfo.put("isUser","0");
                            }

                            documentReference.set(userInfo);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

                else {
                    System.out.println(nameUser + "  " + confirmUserWord);
                    Toast.makeText(getApplicationContext(), nameUser + " dsf " + confirmUserWord , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}