package com.example.movid19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<UploadMovie> uploads;
    private String option;
    public MovieAdapter(Context context, List<UploadMovie> uploads, String option) {
        this.context = context;
        this.uploads = uploads;
        this.option = option;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_edit_movie,parent,false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        final UploadMovie uploadMovieCurrent = uploads.get(position);
        holder.name.setText(uploadMovieCurrent.getTitle());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadMovie selectedItem = uploads.get(position);
                final String selectedKey = selectedItem.getmKey();
//                Toast.makeText(context, "dsd"+selectedKey, Toast.LENGTH_SHORT).show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(uploads.get(position).getImageUri());
                Toast.makeText(context, ""+selectedItem.getImageUri(), Toast.LENGTH_SHORT).show();
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(option);
                        databaseReference.child(selectedKey).removeValue();
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditUpdateMovieActivity.class);
                intent.putExtra("option",option);
                intent.putExtra("key",uploads.get(position).getmKey());
                intent.putExtra("datas", uploads.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public Button deleteBtn;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.row_edit_movie_name);
            deleteBtn = itemView.findViewById(R.id.row_edit_movie_delete_btn);
        }
    }
}
