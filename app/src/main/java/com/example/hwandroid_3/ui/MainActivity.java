package com.example.hwandroid_3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.hwandroid_3.DetailsActivity;
import com.example.hwandroid_3.R;
import com.example.hwandroid_3.data.model.Film;
import com.example.hwandroid_3.data.remote.GhibliApi;
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
        init();
        getFilms();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerVFilm);
        films = new ArrayList<>();
        adapter = new FilmAdapter(films,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnclick(new FilmAdapter.Onclick() {
            @Override
            public void onClick(int position) {
                newIntent(position);
                getFilmId(position);
            }
        });

    }

    private void newIntent(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("film_title",adapter.films.get(position).getTitle());
        intent.putExtra("film_des",adapter.films.get(position).getDescription());
        //Log.d("filmId",adapter.films.get(position).getId());
        startActivity(intent);
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
                        Log.i("fId", response.body().getId());
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