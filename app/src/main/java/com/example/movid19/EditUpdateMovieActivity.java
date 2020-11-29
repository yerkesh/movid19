package com.example.movid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EditUpdateMovieActivity extends AppCompatActivity {
    private String mOption, mKey;
    private ImageView mImageView;
    private Button mSaveBtn;
    private EditText mTitle, mDescription, mID;
    private UploadMovie datas;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_update_movie);

        mImageView = findViewById(R.id.edit_update_movie_image);
        mSaveBtn = findViewById(R.id.edit_update_movie_button);
        mTitle = findViewById(R.id.edit_update_movie_title);
        mDescription = findViewById(R.id.edit_update_movie_description);
        mID = findViewById(R.id.edit_update_movie_id);

        mKey = getIntent().getStringExtra("key");
        mOption = getIntent().getStringExtra("option");
        datas = (UploadMovie) getIntent().getSerializableExtra("datas");

        Picasso.with(this).load(datas.getImageUri()).centerCrop().fit().into(mImageView);

        mTitle.setText(datas.getTitle());
        mDescription.setText(datas.getDescription());
        mID.setText(datas.getmKey());


        mDatabaseReference = FirebaseDatabase.getInstance().getReference(mOption);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDescription.getText().toString().isEmpty() && !mTitle.getText().toString().isEmpty()) {
                    mDatabaseReference.child(datas.getmKey()).child("description").setValue(mDescription.getText().toString());
                    mDatabaseReference.child(datas.getmKey()).child("title").setValue(mTitle.getText().toString());
                    Toast.makeText(EditUpdateMovieActivity.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditUpdateMovieActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}