package com.example.movid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private boolean valid;
    private EditText name, surname, email, phone;
    private Button continue_btn;
    private TextView LinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.signup_edit_name);
        surname = findViewById(R.id.signup_edit_surname);
        email = findViewById(R.id.signup_edit_email);
        phone = findViewById(R.id.signup_edit_phone);

        continue_btn = findViewById(R.id.signup_btn_signup);
        LinkLogin = findViewById(R.id.signup_text_signin);


        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(surname);
                checkField(email);
                checkField(phone);
                if (valid) {
                    Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                    intent.putExtra("Name",getName());
                    intent.putExtra("Surname",getSurname());
                    intent.putExtra("Email",getEmail());
                    intent.putExtra("Phone",getPhone());
                    startActivity(intent);
                }
            }
        });

        LinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }
    public boolean checkField(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("ERROR");
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    public String getName() {
        return name.getText().toString();
    }

    public String getSurname() {
        return surname.getText().toString();
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getPhone() {
        return phone.getText().toString();
    }

}