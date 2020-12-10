package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BasketUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UploadMovie> uploadMovies;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private BasketRecycler basketRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_user);

        progressBar = findViewById(R.id.basket_progress);

        uploadMovies = new ArrayList<>();

        recyclerView = findViewById(R.id.basket_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        basketRecycler = new BasketRecycler(this,uploadMovies);
        recyclerView.setAdapter(basketRecycler);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserBasket").child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadMovies.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    UploadMovie uploadMovie = postSnapshot.getValue(UploadMovie.class);
                    uploadMovie.setmKey(postSnapshot.getKey());
                    uploadMovies.add(uploadMovie);
                }
                basketRecycler.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BasketUserActivity.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}