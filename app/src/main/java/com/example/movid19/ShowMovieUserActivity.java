package com.example.movid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ShowMovieUserActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_user);

        imageView = findViewById(R.id.user_movie_selected_image);
        title = findViewById(R.id.user_movie_selected_text);
        desc = findViewById(R.id.user_movie_selected_description);

        Intent intent = getIntent();
        UploadMovie uploadMovie = (UploadMovie) intent.getSerializableExtra("details");

        Picasso.with(this).load(uploadMovie.getImageUri()).placeholder(R.mipmap.ic_launcher_round).transform(new PicassoCircleTransformation()).into(imageView);
        title.setText(uploadMovie.getTitle());
        desc.setText(uploadMovie.getDescription());
    }
}