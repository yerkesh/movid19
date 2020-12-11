package com.example.movid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {
    private TextView name, surname, email, phone,active;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.profile_name);
        surname = findViewById(R.id.profile_surname);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_phone);
        active = findViewById(R.id.profile_isactive);

        documentReference = FirebaseFirestore.getInstance().collection("Users").document(
                FirebaseAuth.getInstance().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Note note = documentSnapshot.toObject(Note.class);
                name.setText(note.getName());
                surname.setText(note.getSurname());
                email.setText(note.getEmail());
                phone.setText(note.getPhoneNumber());
                if (note.getIsActive().equals("1")) {
                    active.setText("Your account is active");
                }
                else {
                    active.setText("Your account is not active");
                }
            }
        });

    }
}