package com.example.movid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity {
    private Button logout;
    private String[] typesOfUsers = {"Users","Managers"};
    private int[] layoutsOfUsers = {R.id.users, R.id.managers};
    private int[] layoutsOfMovies = {R.id.new_releases, R.id.trending_now, R.id.action, R.id.tv_shows};
    private String[] titlesOfCatalog = {"New Releases", "Trending Now", "Action", "TV Shows"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        for(int i = 0; i < typesOfUsers.length; i++) {
            LinearLayout linearLayout = findViewById(layoutsOfUsers[i]);
            TextView textView = linearLayout.findViewById(R.id.title_item_or_user);
            textView.setText(typesOfUsers[i]);
            Button add = linearLayout.findViewById(R.id.button_add);
            Button edit = linearLayout.findViewById(R.id.button_edit);
            final int finalI = i;
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                    intent.putExtra("option", "" + finalI);
                    startActivity(intent);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),EditUserActivity.class);
                    intent.putExtra("option", "" + finalI);
                    startActivity(intent);
                }
            });
        }

        for (int i = 0; i < titlesOfCatalog.length; i++) {
            LinearLayout linearLayout = findViewById(layoutsOfMovies[i]);
            TextView textView = linearLayout.findViewById(R.id.title_item_or_user);
            textView.setText(titlesOfCatalog[i]);

            Button add = linearLayout.findViewById(R.id.button_add);
            Button edit = linearLayout.findViewById(R.id.button_edit);

            final int finalI1 = i;
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                    intent.putExtra("option", "" + titlesOfCatalog[finalI1]);
                    startActivity(intent);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),EditMovieActivity.class);
                    intent.putExtra("option", "" + titlesOfCatalog[finalI1]);
                    startActivity(intent);
                }
            });
        }


        logout = findViewById(R.id.main_admin_btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}