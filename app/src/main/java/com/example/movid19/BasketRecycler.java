package com.example.movid19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketRecycler extends RecyclerView.Adapter<BasketRecycler.ListItemHolder> {
    private Context context;
    private List<UploadMovie> uploads;

    public BasketRecycler (Context context, List<UploadMovie> uploads) {
        this.context = context;
        this.uploads = uploads;
    }
    @NonNull
    @Override
    public BasketRecycler.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_basket_user,parent,false);
        return new ListItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketRecycler.ListItemHolder holder, final int position) {
        holder.textView.setText(uploads.get(position).getTitle());
        Picasso.with(context).load(uploads.get(position).getImageUri()).centerCrop().fit().into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowMovieUserActivity.class);
                intent.putExtra("details",uploads.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserBasket").child(
                        FirebaseAuth.getInstance().getUid());
                databaseReference.child(uploads.get(position).getmKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private LinearLayout linearLayout;
        private ImageView delete_img;
        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.basket_image_view);
            textView = itemView.findViewById(R.id.basket_text_view);
            linearLayout = itemView.findViewById(R.id.basket_linear_layout);
            delete_img = itemView.findViewById(R.id.basket_delete_button);
        }
    }
}
