package com.example.hwandroid_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hwandroid_3.data.model.Film;
import com.example.hwandroid_3.data.remote.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private TextView f_Title;
    private TextView f_Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
    }

    private void init() {
        f_Title = findViewById(R.id.f_title);
        f_Description = findViewById(R.id.f_description);
        Intent intent = getIntent();
        if (intent!=null){
            String film_title = intent.getStringExtra("film_title");
            String film_des = intent.getStringExtra("film_des");
//            Log.d("fId",film_title);
            f_Title.setText(film_title);
            f_Description.setText(film_des);
        }
    }

}