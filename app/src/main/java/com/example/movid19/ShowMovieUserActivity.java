package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ShowMovieUserActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title, desc;
    private FloatingActionButton add_button,basket_button;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_user);

        imageView = findViewById(R.id.user_movie_selected_image);
        title = findViewById(R.id.user_movie_selected_text);
        desc = findViewById(R.id.user_movie_selected_description);
        add_button = findViewById(R.id.show_movie_user_add_btn);
        basket_button = findViewById(R.id.show_movie_user_basket_btn);

        Intent intent = getIntent();
        final UploadMovie uploadMovie = (UploadMovie) intent.getSerializableExtra("details");

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserBasket/" + userID);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(uploadMovie).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ShowMovieUserActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShowMovieUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(ShowMovieUserActivity.this, "Clicked successfully", Toast.LENGTH_SHORT).show();
            }
        });
        
        basket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowMovieUserActivity.this, "Basket clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(this).load(uploadMovie.getImageUri()).placeholder(R.mipmap.ic_launcher_round).transform(new PicassoCircleTransformation()).into(imageView);
        title.setText(uploadMovie.getTitle());
        desc.setText(uploadMovie.getDescription());
    }
}