package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainUserActivity extends AppCompatActivity {
    private Button logout;
    private int linearLayout = R.id.row_user_main_newreleases;
    private RecyclerView recyclerView;
    private ArrayList<UploadMovie> uploadMovies;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        logout = findViewById(R.id.main_user_btn_logout);
        uploadMovies = new ArrayList<>();
        LinearLayout linearLayout1 = findViewById(R.id.main_user_new_releases);
        recyclerView = linearLayout1.findViewById(R.id.new_releases_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final UserMovieRecycler userMovieRecycler = new UserMovieRecycler(getApplicationContext(),uploadMovies);
        recyclerView.setAdapter(userMovieRecycler);



        databaseReference = FirebaseDatabase.getInstance().getReference("New Releases");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadMovies.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    UploadMovie uploadMovie = postSnapshot.getValue(UploadMovie.class);
                    uploadMovie.setmKey(postSnapshot.getKey());
                    uploadMovies.add(uploadMovie);
                }

                userMovieRecycler.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainUserActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}