package com.example.movid19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagerUserListrecycler extends RecyclerView.Adapter<ManagerUserListrecycler.MyUserHolder>  {
    private Context context;
    private String[] usersName;
    private ArrayList<Note> notes;

    public ManagerUserListrecycler(Context context, String[] usersName, ArrayList<Note> notes) {
        this.context = context;
        this.usersName = usersName;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ManagerUserListrecycler.MyUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_manager_list_users,parent,false);
        return new MyUserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerUserListrecycler.MyUserHolder holder, final int position) {
        holder.name.setText(usersName[position]);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clickeddd", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ManagerUserEditActivity.class);
                intent.putExtra("info",notes.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    
    public class MyUserHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public MyUserHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.manager_username);
        }
    }
}
