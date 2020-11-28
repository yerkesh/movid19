package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class EditMovieActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;

    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseReference;
    private List<UploadMovie> muploadMovies;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        mProgressCircle = findViewById(R.id.edit_movie_progressbar);

        mRecyclerView = findViewById(R.id.edit_movie_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        muploadMovies = new ArrayList<>();

        movieAdapter = new MovieAdapter(getApplicationContext(),muploadMovies,getIntent().getStringExtra("option"));
        mRecyclerView.setAdapter(movieAdapter);

        mStorage = FirebaseStorage.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("option"));
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                muploadMovies.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    UploadMovie uploadMovie = postSnapshot.getValue(UploadMovie.class);
                    uploadMovie.setmKey(postSnapshot.getKey());
                    muploadMovies.add(uploadMovie);
                }

                movieAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditMovieActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}