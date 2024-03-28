package com.emilandahtesam.moviehub.Movie;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emilandahtesam.moviehub.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView releaseDate;
    private TextView voteAverage;
    private ImageView poster;

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(TextView releaseDate) {
        this.releaseDate = releaseDate;
    }

    public TextView getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(TextView voteAverage) {
        this.voteAverage = voteAverage;
    }

    public ImageView getPoster() {
        return poster;
    }

    public void setPoster(ImageView poster) {
        this.poster = poster;
    }

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        setTitle(itemView.findViewById(R.id.titleText));
        setReleaseDate(itemView.findViewById(R.id.releaseDateText));
        setVoteAverage(itemView.findViewById(R.id.voteAverageText));
        setPoster(itemView.findViewById(R.id.posterImage));
        getTitle().setMaxLines(2);
        getTitle().setEllipsize(TextUtils.TruncateAt.END);
    }

    public void bind(final Movie item, final MovieAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
}
