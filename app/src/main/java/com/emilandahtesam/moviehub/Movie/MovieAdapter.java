package com.emilandahtesam.moviehub.Movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emilandahtesam.moviehub.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    private List<Movie> movies;
    private final OnItemClickListener listener;

    public MovieAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie, getListener());
        holder.getTitle().setText(movie.getTitle());
        holder.getReleaseDate().setText(movie.getRelease_date());
        holder.getVoteAverage().setText(movie.getVote_average());
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster_path())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.getPoster());
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }
}
