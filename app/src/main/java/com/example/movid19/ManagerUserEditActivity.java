package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManagerUserEditActivity extends AppCompatActivity {
    private TextView name, surname, email, phone;
    private Button block, active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_edit);

        name = findViewById(R.id.manager_username_up);
        surname = findViewById(R.id.manager_surname_up);
        email = findViewById(R.id.manager_email_up);
        phone = findViewById(R.id.manager_phone_up);

        block = findViewById(R.id.manager_block_btn_up);
        active = findViewById(R.id.manager_activate_btn_up);

        final Note info = (Note) getIntent().getSerializableExtra("info");

        name.setText(info.getName());
        surname.setText(info.getSurname());
        email.setText(info.getEmail());
        phone.setText(info.getPhoneNumber());

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Users").document(info.getDocumentId())
                        .update("isActive","0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManagerUserEditActivity.this, "The User is blocked successfully ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagerUserEditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Users").document(info.getDocumentId())
                        .update("isActive","1").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManagerUserEditActivity.this, "The User is activated successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagerUserEditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}