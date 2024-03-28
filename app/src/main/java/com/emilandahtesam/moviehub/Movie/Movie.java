package com.emilandahtesam.moviehub.Movie;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private String release_date;
    private String vote_average;
    private String poster_path;
    private String overview;
    private static final String postersCDN = "https://image.tmdb.org/t/p/w500/";
    private static final String placeHolderIMG = "https://critics.io/img/movies/poster-placeholder.png";

    public Movie(String title, String release_date, String vote_average, String poster_path, String overview) {
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public String getTitle() {
        if (title.isEmpty()) {
            return "N/A";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        if (release_date.isEmpty()) {
            return "N/A";
        }
        List<String> releaseDateReversed = Arrays.asList(release_date.split("-"));
        Collections.reverse(releaseDateReversed);
        StringBuilder formattedReleaseDate = new StringBuilder();
        for (int i = 0; releaseDateReversed.size() > i; i++) {
            formattedReleaseDate.append(releaseDateReversed.get(i));
            if (!(releaseDateReversed.size() - 1 == i)) {
                formattedReleaseDate.append("/");
            }
        }
        return formattedReleaseDate.toString();
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        if (overview.isEmpty()) {
            return "There is no overview for this movie in the database.";
        }
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        if (poster_path == null) {
            return placeHolderIMG;
        }
        return postersCDN + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        float parsedVoteAverage = Math.round(Float.parseFloat(vote_average) * 10);
        int numberOfDigits = 2;
        if (parsedVoteAverage == 100) {
            numberOfDigits = 3;
        } else if (parsedVoteAverage < 10 && parsedVoteAverage > 0) {
            numberOfDigits = 1;
        } else if (parsedVoteAverage == 0) {
            return "N/A";
        }
        return Float.toString(parsedVoteAverage).substring(0, numberOfDigits) + " %";
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}
