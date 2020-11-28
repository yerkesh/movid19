package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class EditUpdateUserActivity extends AppCompatActivity {
    private Note datas;
    private EditText name, surname, phonenumber;
    private Button makeManager, makeUser, activate, block, save;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_update_user);

        datas = (Note) getIntent().getExtras().getSerializable("data");

        name = findViewById(R.id.edit_update_user_name);
        surname = findViewById(R.id.edit_update_user_surname);
        phonenumber = findViewById(R.id.edit_update_user_phone);
        makeManager = findViewById(R.id.edit_update_user_manager_button);
        makeUser = findViewById(R.id.edit_update_user_user_button);
        save = findViewById(R.id.edit_update_user_save_button);
        activate = findViewById(R.id.edit_update_user_activate_button);
        block = findViewById(R.id.edit_update_user_block_button);


        name.setText(datas.getName());
        surname.setText(datas.getSurname());
        phonenumber.setText(datas.getPhoneNumber());

        makeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.document(datas.getDocumentId()).update("isManager","1");
                collectionReference.document(datas.getDocumentId()).update("isUser","0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUpdateUserActivity.this, "Changed to Manager", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUpdateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        makeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.document(datas.getDocumentId()).update("isManager","0");
                collectionReference.document(datas.getDocumentId()).update("isUser","1").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUpdateUserActivity.this, "Changed to User", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUpdateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.document(datas.getDocumentId()).update("isActive","1").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUpdateUserActivity.this, "User is active", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUpdateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.document(datas.getDocumentId()).update("isActive","0")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditUpdateUserActivity.this, "User is blocked", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUpdateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty()
                && !phonenumber.getText().toString().isEmpty()) {
                    collectionReference.document(datas.getDocumentId()).update("Surname",surname.getText().toString());
                    collectionReference.document(datas.getDocumentId()).update("PhoneNumber",phonenumber.getText().toString());
                    collectionReference.document(datas.getDocumentId()).update("Name",name.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditUpdateUserActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditUpdateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(EditUpdateUserActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        

        
        Toast.makeText(this, ""+datas.getDocumentId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.document(datas.getDocumentId()).addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    return;
                }
                if (value.exists()) {
                    name.setText(value.getString("Name"));
                    surname.setText(value.getString("Surname"));
                    phonenumber.setText(value.getString("PhoneNumber"));
                }
            }
        });
    }


}