package com.example.hwandroid_3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hwandroid_3.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView f_Title, film_Id;
    private TextView f_Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
    }

    private void init() {
        f_Title = findViewById(R.id.f_title);
        film_Id = findViewById(R.id.film_Id);
        f_Description = findViewById(R.id.f_description);
        Intent intent = getIntent();
        if (intent != null) {
            String film_id = intent.getStringExtra("film_Id");
            String film_title = intent.getStringExtra("film_title");
            String film_des = intent.getStringExtra("film_des");
            film_Id.setText(film_id);
            f_Title.setText(film_title);
            f_Description.setText(film_des);
        }
    }
}