package com.example.movid19;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EditUserActivity extends AppCompatActivity {
    private RecyclerView listUsers;
    private String namesUsers[];
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("Users");
    private ArrayList<Note> mNotes;
    private String option;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
//        textV = findViewById(R.id.user_edit_test);

        listUsers = findViewById(R.id.listusers);

        System.out.println(collectionReference.limit(2));
        System.out.println(collectionReference.toString());

        option = getIntent().getStringExtra("option");
        mNotes = new ArrayList<Note>();
        loadDataFromFirebase();


    }



    public void loadDataFromFirebase() {

//        loadNodes(textV);
        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    if (option != null) {
                        if (note.getIsUser().equals("1") && option.equals("0"))
                            mNotes.add(note);
                        else if (note.getIsManager().equals("1") && option.equals("1")) {
                            mNotes.add(note);
                        }
                    }
                }
                namesUsers = new String[mNotes.size()];
                for (int i = 0; i < mNotes.size(); i++) {

                    namesUsers[i] = mNotes.get(i).getName();

                }

                RecyclerAdapter adapter = new RecyclerAdapter(EditUserActivity.this,namesUsers,mNotes,option);
                listUsers.setAdapter(adapter);
                listUsers.setLayoutManager(new LinearLayoutManager(EditUserActivity.this));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
    }
}