package com.affinityapps.popularmoviesstage1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener{

    private Context context;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieData;
    private RequestQueue requestQueue;
    public static final String EXTRA_URL = "movieUrl";
    public static final String EXTRA_TITLE = "movieTitle";
    public static final String EXTRA_RELEASE_DATE = "movieReleaseDate";
    public static final String EXTRA_VOTE_AVERAGE = "movieVoteAverage";
    public static final String EXTRA_PLOT_SYNOPSIS = "moviePlotSynopsis";
    private static final String movieBaseUrl="https://image.tmdb.org/t/p/w185";
    private static final String mostPopularUrl = "https://api.themoviedb.org/3/movie/popular?api_key=0985d7dead91a911264472433eb9c5dc";
    private static final String topRatedUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=0985d7dead91a911264472433eb9c5dc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieData = new ArrayList<>();

        recyclerView = findViewById(R.id.movie_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        requestQueue = Volley.newRequestQueue(this);
        parseMovieData(mostPopularUrl);
    }

    public void parseMovieData(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject results = jsonArray.getJSONObject(i);

                                String posterPath = results.getString("poster_path");
                                String titlePath = results.getString("title");
                                String releaseDatePath = results.getString("release_date");
                                int voteAveragePath = results.getInt("vote_average");
                                String plotSynopsisPath = results.getString("overview");

                                movieData.add(new Movie(movieBaseUrl + posterPath, titlePath, releaseDatePath, voteAveragePath, plotSynopsisPath));
                            }

                            movieAdapter = new MovieAdapter(MainActivity.this, movieData);
                            recyclerView.setAdapter(movieAdapter);
                            movieAdapter.setOnItemClickListener(MainActivity.this);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.most_popular_option:
                movieData.clear();
                parseMovieData(mostPopularUrl);
                movieAdapter.notifyDataSetChanged();

            case R.id.top_rated_option:
                movieData.clear();
                parseMovieData(topRatedUrl);
                movieAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Movie movie = movieData.get(position);

        intent.putExtra(EXTRA_URL, movie.getMovieImageUrl());
        intent.putExtra(EXTRA_TITLE, movie.getTitle());
        intent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseDate());
        intent.putExtra(EXTRA_VOTE_AVERAGE, movie.getVoteAverage());
        intent.putExtra(EXTRA_PLOT_SYNOPSIS, movie.getPlotSynopsis());
        startActivity(intent);
    }
}
