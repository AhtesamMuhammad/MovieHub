package com.emilandahtesam.moviehub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emilandahtesam.moviehub.Movie.Movie;
import com.emilandahtesam.moviehub.Movie.MovieAdapter;
import com.emilandahtesam.moviehub.API.API;
import com.emilandahtesam.moviehub.API.APICallback;
import com.emilandahtesam.moviehub.databinding.FragmentMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private SharedPreferences sharedPreferences;
    private MovieAdapter movieAdapter;
    private String lastMoviesCategoryType;
    private String lastLanguage;
    private String lastSearch;
    private String lastGridViewStatus;
    private List<Movie> moviesList;
    private List<Movie> moviesSearchList;
    private String[] movieCategoriesValues;
    private String[] movieCategoriesEntries;
    private String[] languageListValues;
    private String[] languageListEntries;
    private Runnable runnable;

    private void hideVirtualKeyboard() {
        InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
        binding.searchBox.clearFocus();
    }

    private void showSnackbarOnTop(String snackbarContent) {
        Snackbar snackbar = Snackbar.make(requireView(), snackbarContent, Snackbar.LENGTH_LONG).setTextMaxLines(3);
        View snackbarView = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        params.topMargin = 100;
        snackbarView.setLayoutParams(params);
        snackbar.show();
    }

    private void restartFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setRecylcerViewToLinearLayout() {
        binding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.gridStatusSwitch.setChecked(false);
        lastGridViewStatus = "false";
    }

    private void setRecylcerViewToGridLayout() {
        binding.moviesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.gridStatusSwitch.setChecked(true);
        lastGridViewStatus = "true";
    }

    private void setFeedbackTextVisibility(boolean visible) {
        if (visible) {
            binding.feedbackText.setText(getString(R.string.no_movies_found_text));
            binding.feedbackText.setVisibility(View.VISIBLE);
        } else {
            binding.feedbackText.setVisibility(View.GONE);
        }
    }

    private void setDefaultMovieCategoryConfiguration() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movies_category", movieCategoriesValues[0]);
        editor.apply();
    }

    private void setDefaultLanguageConfiguration() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movies_language", languageListValues[0]);
        editor.apply();
    }

    private void setGridViewStatusConfiguration(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("grid_view_status", value);
        editor.apply();
    }

    private void updateLastConfigurationValues() {
        lastMoviesCategoryType = sharedPreferences.getString("movies_category", "popular");
        lastLanguage = sharedPreferences.getString("movies_language", "en");
    }

    private void updateMovieCategoryHeader(boolean searchHeader) {
        if (searchHeader) {
            binding.movieCategoryText.setText(getString(R.string.search_results_text));
            return;
        }

        String category = getString(R.string.movies_text);
        for (int i = 0; i < movieCategoriesValues.length; i++) {
            if (movieCategoriesValues[i].equals(lastMoviesCategoryType)) {
                category = movieCategoriesEntries[i];
                break;
            }
        }
        if (category.equals(getString(R.string.movies_text))) {
            setDefaultMovieCategoryConfiguration();
            lastMoviesCategoryType = movieCategoriesValues[0];
            category = movieCategoriesEntries[0];
        }
        binding.movieCategoryText.setText(category);
    }

    private void updateMovieLanguageHeader() {
        String language = getString(R.string.language_text, "Default");
        for (int i = 0; i < languageListValues.length; i++) {
            if (languageListValues[i].equals(lastLanguage)) {
                language = getString(R.string.language_text, languageListEntries[i]);
                break;
            }
        }
        if (language.equals(getString(R.string.language_text, "Default"))) {
            setDefaultLanguageConfiguration();
            lastLanguage = languageListValues[0];
            language = getString(R.string.language_text, languageListEntries[0]);
        }
        binding.movieLanguageText.setText(language);
    }

    private void updateGridViewStatus() {
        String gridViewStatus = sharedPreferences.getString("grid_view_status", "false");
        if (gridViewStatus.equals("true")) {
            setRecylcerViewToGridLayout();
        } else if (gridViewStatus.equals("false")) {
            setRecylcerViewToLinearLayout();
        } else {
            setGridViewStatusConfiguration("false");
            setRecylcerViewToLinearLayout();
        }
    }

    private void setupMovieAdapter() {
        movieAdapter = new MovieAdapter(new ArrayList<>(), item -> {
            MainFragmentDirections.ActionMainFragmentToMovieDetailFragment action =
                    MainFragmentDirections.actionMainFragmentToMovieDetailFragment(
                            item.getTitle(),
                            item.getRelease_date(),
                            item.getVote_average(),
                            item.getPoster_path(),
                            item.getOverview()
                    );
            NavHostFragment.findNavController(MainFragment.this).navigate(action);
        });
        binding.moviesRecyclerView.setAdapter(movieAdapter);
    }

    private void loadMoviesInRecyclerView(boolean searchExecuted) {
        binding.searchBox.setEnabled(true);
        updateMovieCategoryHeader(searchExecuted);
        if (searchExecuted) {
            movieAdapter.setMovies(moviesSearchList);
            setFeedbackTextVisibility(moviesSearchList.size() == 0);
        } else {
            movieAdapter.setMovies(moviesList);
            setFeedbackTextVisibility(false);
        }
    }

    private boolean moviesCategoryOrLanguageChangedInConfiguration() {
        boolean result = false;
        if (!sharedPreferences.getString("movies_category", "popular").equals(lastMoviesCategoryType)) {
            result = true;
        } else if (!sharedPreferences.getString("movies_language", "en").equals(lastLanguage)) {
            result = true;
        }
        return result;
    }

    private boolean gridViewStatusChangedInConfiguration() {
        return !sharedPreferences.getString("grid_view_status", "false").equals(lastGridViewStatus);
    }

    private boolean searchIsEmptyOrEqualToPreviousOne() {
        String searchBoxText = binding.searchBox.getText().toString();
        if (searchBoxText.isEmpty()) {
            loadMoviesInRecyclerView(false);
            return true;
        } else if (searchBoxText.equalsIgnoreCase(lastSearch)) {
            loadMoviesInRecyclerView(true);
            return true;
        }
        return false;
    }

    private void getMoviesFromAPI() {
        API.getMoviesFromAPI(new APICallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                requireActivity().runOnUiThread(() -> {
                    moviesList = movies;
                    loadMoviesInRecyclerView(false);
                });
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }, lastMoviesCategoryType, lastLanguage);
    }

    private void getMoviesFromAPIUsingQuery(String query) {
        API.getMoviesFromAPIUsingQuery(new APICallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                requireActivity().runOnUiThread(() -> {
                    moviesSearchList = movies;
                    loadMoviesInRecyclerView(true);
                });
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }, query, lastLanguage);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        if (savedInstanceState != null) {
            lastSearch = savedInstanceState.getString("last_search");
            moviesList = (List<Movie>) savedInstanceState.getSerializable("movies_list");
            moviesSearchList = (List<Movie>) savedInstanceState.getSerializable("movies_search_list");
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        movieCategoriesValues = getResources().getStringArray(R.array.movie_categories_list_values);
        movieCategoriesEntries = getResources().getStringArray(R.array.movie_categories_list_entries);
        languageListValues = getResources().getStringArray(R.array.language_list_values);
        languageListEntries = getResources().getStringArray(R.array.language_list_entries);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateGridViewStatus();
        setupMovieAdapter();
        updateLastConfigurationValues();
        updateMovieCategoryHeader(false);
        updateMovieLanguageHeader();

        if (moviesList == null) {
            getMoviesFromAPI();
        }

        binding.gridStatusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setRecylcerViewToGridLayout();
                setGridViewStatusConfiguration("true");
            } else {
                setRecylcerViewToLinearLayout();
                setGridViewStatusConfiguration("false");
            }
        });

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (runnable != null) {
                    requireView().removeCallbacks(runnable);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchIsEmptyOrEqualToPreviousOne()) {
                    return;
                }
                runnable = () -> {
                    String search = binding.searchBox.getText().toString();
                    getMoviesFromAPIUsingQuery(search);
                    lastSearch = search;
                };
                requireView().postDelayed(runnable, 500);
            }
        });

        binding.imageButton.setOnClickListener(View -> {
            hideVirtualKeyboard();
            if (binding.searchBox.getText().toString().isEmpty()) {
                return;
            }
            binding.searchBox.setText("");
            showSnackbarOnTop(getString(R.string.searchbox_cleared_text));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (moviesCategoryOrLanguageChangedInConfiguration()) {
            restartFragment();
        } else if (gridViewStatusChangedInConfiguration()) {
            updateGridViewStatus();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("movies_list", (Serializable) moviesList);
        bundle.putSerializable("movies_search_list", (Serializable) moviesSearchList);
        bundle.putString("last_search", lastSearch);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
