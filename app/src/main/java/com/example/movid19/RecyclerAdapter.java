package com.example.movid19;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private String names[];
    private ArrayList<Note> arrayList;
    private String option;
    public RecyclerAdapter(Context context, String names[], ArrayList<Note> arrayList, String option) {
        this.names = names;
        this.context = context;
        this.arrayList = arrayList;
        this.option = option;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_edit_user,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.usersName.setText(names[position]);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditUserActivity.class);
                deleteSelectedRow(position);
                intent.putExtra("option",option);
                context.startActivities(new Intent[]{intent});
            }
        });
        holder.usersName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditUpdateUserActivity.class);
                intent.putExtra("data", arrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    public void deleteSelectedRow(int position) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Users");
        collectionReference
                .document(arrayList.get(position).getDocumentId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView usersName;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usersName = itemView.findViewById(R.id.user_edit_users_name);
            delete = itemView.findViewById(R.id.edit_user_delete_button);
        }
    }
}
