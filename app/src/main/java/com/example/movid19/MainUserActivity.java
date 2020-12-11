package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private RecyclerView recyclerView, trendingRecycler, actionRecycler, tvRecyler;
    private ArrayList<UploadMovie> uploadMovies;
    private DatabaseReference databaseReference;
    private ArrayList<UploadMovie> trendingList, actionList, tvList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        logout = findViewById(R.id.main_user_btn_logout);

        progressBar = findViewById(R.id.user_main_progressbar);

        uploadMovies = new ArrayList<>();
        trendingList = new ArrayList<>();
        actionList = new ArrayList<>();
        tvList = new ArrayList<>();
        LinearLayout linearLayout1 = findViewById(R.id.main_user_new_releases);
        recyclerView = linearLayout1.findViewById(R.id.new_releases_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final UserMovieRecycler userMovieRecycler = new UserMovieRecycler(getApplicationContext(),uploadMovies);
        recyclerView.setAdapter(userMovieRecycler);

        LinearLayout trendingLayout = findViewById(R.id.main_user_trending);
        trendingRecycler = trendingLayout.findViewById(R.id.new_releases_recyclerview);
        LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        trendingRecycler.setLayoutManager(trendingLayoutManager);
        UserMovieRecycler trendingAdapter = new UserMovieRecycler(this,trendingList);
        trendingRecycler.setAdapter(trendingAdapter);

        LinearLayout actionLayout = findViewById(R.id.main_user_action);
        actionRecycler = actionLayout.findViewById(R.id.new_releases_recyclerview);
        LinearLayoutManager actionLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        actionRecycler.setLayoutManager(actionLayoutManager);
        UserMovieRecycler actionAdapter = new UserMovieRecycler(this,actionList);
        actionRecycler.setAdapter(actionAdapter);

        LinearLayout tvLayout = findViewById(R.id.main_user_tvshows);
        tvRecyler = tvLayout.findViewById(R.id.new_releases_recyclerview);
        LinearLayoutManager tvLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tvRecyler.setLayoutManager(tvLayoutManager);
        UserMovieRecycler tvAdapter = new UserMovieRecycler(this,tvList);
        tvRecyler.setAdapter(tvAdapter);



//        databaseReference = FirebaseDatabase.getInstance().getReference("New Releases");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                uploadMovies.clear();
//
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    UploadMovie uploadMovie = postSnapshot.getValue(UploadMovie.class);
//                    uploadMovie.setmKey(postSnapshot.getKey());
//                    uploadMovies.add(uploadMovie);
//                }
//
//                userMovieRecycler.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainUserActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        getMovieData("New Releases", userMovieRecycler, uploadMovies);
        getMovieData("Trending Now", trendingAdapter, trendingList);
        getMovieData("Action", actionAdapter, actionList);
        getMovieData("TV Shows", tvAdapter, tvList);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    public void getMovieData(String reference, final UserMovieRecycler recycler, final ArrayList uploads) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(reference);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploads.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    UploadMovie uploadMovie = postSnapshot.getValue(UploadMovie.class);
                    uploadMovie.setmKey(postSnapshot.getKey());
                    uploads.add(uploadMovie);
                }

                recycler.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainUserActivity.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.item2) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}