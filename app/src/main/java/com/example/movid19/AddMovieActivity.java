package com.example.movid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddMovieActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImage, upload;
    private EditText title, description;
    private ImageView imageView;
    private ProgressBar progressBar;

    private Uri imageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Task mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        chooseImage = findViewById(R.id.movie_add_choose_img_btn);
        upload = findViewById(R.id.movie_add_upload_btn);
        title = findViewById(R.id.movie_add_title);
        description = findViewById(R.id.movie_add_description);
        imageView = findViewById(R.id.movie_add_image_view);
        progressBar = findViewById(R.id.movie_add_progressbar);

        mStorageRef = FirebaseStorage.getInstance().getReference(getIntent().getStringExtra("option"));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("option"));
        Toast.makeText(this, "" + getIntent().getStringExtra("option"), Toast.LENGTH_SHORT).show();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && !mUploadTask.isComplete()) {
                    Toast.makeText(AddMovieActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });
    }

    private String getFileExtention(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap miem = MimeTypeMap.getSingleton();
        return miem.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile() {
//        if (imageUri != null) {
//            StorageReference fileReference = mStorageRef.child(title.getText().toString() +
//                    System.currentTimeMillis() + "." +
//                    getFileExtention(imageUri));
//            mUploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(0);
//                        }
//                    },500);
//                    Toast.makeText(AddMovieActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
//                    UploadMovie uploadMovie = new UploadMovie(title.getText().toString(),description.getText().toString(),taskSnapshot.getStorage().getDownloadUrl().toString());
//                    String uploadId = mDatabaseRef.push().getKey();
//                    mDatabaseRef.child(uploadId).setValue(uploadMovie);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(AddMovieActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
//                    progressBar.setProgress((int)progress);
//                }
//            });
//        }
        if (imageUri != null)
        {   final StorageReference fileReference = mStorageRef.child(title.getText().toString() +
                    System.currentTimeMillis() + "." +
                    getFileExtention(imageUri));
            mUploadTask = fileReference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        Log.e("TAG", "then: " + downloadUri.toString());


                        UploadMovie upload = new UploadMovie(title.getText().toString(),description.getText().toString(),
                                downloadUri.toString());

                        mDatabaseRef.push().setValue(upload);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);
                            }
                        },500);
                        Toast.makeText(AddMovieActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null) {
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).placeholder(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
}