package com.example.hwandroid_3.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hwandroid_3.DetailsActivity;
import com.example.hwandroid_3.R;
import com.example.hwandroid_3.data.model.Film;
import com.example.hwandroid_3.data.remote.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CinemaHolder> {
    Onclick onclick;
    public List<Film> films = new ArrayList<>();
    private Context context;

    public FilmAdapter(List<Film> films, Context context) {
        this.films = films;
        this.context = context;
    }

    @NonNull
    @Override
    public CinemaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_vfilm, parent, false);
        return new CinemaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaHolder holder, int position) {
        //load item (Film)
      //  Film film = films.get(position);
        holder.bind(films.get(position));

    }

    private void showDetail(Film film) {
        Intent nextActivity = new Intent(context, DetailsActivity.class);
        nextActivity.putExtra("film", (Parcelable) film);
        context.startActivity(nextActivity);
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void setElement(Film body) {
        films.add(0, body);
        notifyDataSetChanged();
    }

    public class CinemaHolder extends RecyclerView.ViewHolder {
        private final TextView tvFilm;

        public CinemaHolder(@NonNull View itemView) {
            super(itemView);
            tvFilm = itemView.findViewById(R.id.tv_film);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick.onClick(getAdapterPosition());
                }
            });
        }

        public void bind(Film film) {
            tvFilm.setText(film.getTitle());
        }
    }

    public interface Onclick {
        void onClick(int position);
    }
}
