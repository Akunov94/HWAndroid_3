package com.example.hwandroid_3.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.hwandroid_3.R;
import com.example.hwandroid_3.data.model.Film;
import com.example.hwandroid_3.data.remote.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private List<Film> films = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        init();
        getFilms();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerVFilm);
        films = new ArrayList<>();
        adapter = new FilmAdapter(films,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnclick(position -> getFilmId(position));

    }


    private void getFilms() {
        RetrofitFactory
                .getInstance()
                .getFilm()
                .enqueue(new Callback<List<Film>>() {
                    @Override
                    public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                        if (response.isSuccessful()) {
                            for (Film film : response.body()) {
                                adapter.setElement(film);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Film>> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    private void getFilmId(int position){
        String id = adapter.films.get(position).getId();
        RetrofitFactory.getInstance().getFilmId(id).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("film_Id",response.body().getId());
                        intent.putExtra("film_title",response.body().getTitle());
                        intent.putExtra("film_des",response.body().getDescription());
                        startActivity(intent);
                        Log.i("f_Id", response.body().getId());
                    }
                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {

            }
        });
    }
}