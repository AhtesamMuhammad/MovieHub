package com.emilandahtesam.moviehub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.emilandahtesam.moviehub.databinding.FragmentMovieDetailBinding;

public class MovieDetailFragment extends Fragment {
    private FragmentMovieDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieDetailFragmentArgs arguments = MovieDetailFragmentArgs.fromBundle(getArguments());
        binding.detailTitleText.setText(arguments.getTitle());
        binding.detailReleaseDateText.setText(arguments.getReleaseDate());
        binding.detailVoteAverageText.setText(arguments.getVoteAverage());
        binding.detailOverviewText.setText(arguments.getOverview());
        Glide.with(requireContext())
                .load(arguments.getPoster())
                .placeholder(R.drawable.placeholder_image)
                .into(binding.detailPosterImage);
    }
}
