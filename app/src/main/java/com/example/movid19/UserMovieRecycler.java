
package com.example.movid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserMovieRecycler extends RecyclerView.Adapter<UserMovieRecycler.ListMovieHolder> {

    private Context context;
    private List<UploadMovie> uploads;

    public UserMovieRecycler(Context context, List<UploadMovie> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public ListMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user_main_newreleases,parent, false);
        return new ListMovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieHolder holder, int position) {
        holder.textView.setText(uploads.get(position).getTitle());
        Picasso.with(context).load(uploads.get(position).getImageUri()).centerCrop().fit().into(holder.imageView);
        
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ListMovieHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ListMovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item_view);
            textView = itemView.findViewById(R.id.text_item_view);
        }
    }
}
