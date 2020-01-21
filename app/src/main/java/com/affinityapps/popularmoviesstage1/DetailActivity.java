package com.affinityapps.popularmoviesstage1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import static com.affinityapps.popularmoviesstage1.MainActivity.EXTRA_PLOT_SYNOPSIS;
import static com.affinityapps.popularmoviesstage1.MainActivity.EXTRA_RELEASE_DATE;
import static com.affinityapps.popularmoviesstage1.MainActivity.EXTRA_TITLE;
import static com.affinityapps.popularmoviesstage1.MainActivity.EXTRA_URL;
import static com.affinityapps.popularmoviesstage1.MainActivity.EXTRA_VOTE_AVERAGE;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String movieImageUrl2 = intent.getStringExtra(EXTRA_URL);
        String movieTitle2 = intent.getStringExtra(EXTRA_TITLE);
        String movieReleaseDate2 = intent.getStringExtra(EXTRA_RELEASE_DATE);
        int movieVoteAverage2 = intent.getIntExtra(EXTRA_VOTE_AVERAGE, 0);
        String moviePlotSynopsis2 = intent.getStringExtra(EXTRA_PLOT_SYNOPSIS);

        ImageView imageView = findViewById(R.id.detail_movie_poster);
        TextView textViewTitle = findViewById(R.id.detail_movie_title);
        TextView textViewReleaseDate = findViewById(R.id.detail_release_date);
        TextView textViewVoteAverage = findViewById(R.id.detail_vote_average);
        TextView textViewPlotSynopsis = findViewById(R.id.detail_plot_synopsis);

        Picasso.get().load(movieImageUrl2).
                placeholder(R.drawable.ic_local_movies_black_24dp).
                error(R.drawable.ic_block_black_24dp).
                into(imageView);

        textViewTitle.setText("Movie Title: \n" + movieTitle2);
        textViewReleaseDate.setText("Release Date: \n" + movieReleaseDate2);
        textViewVoteAverage.setText("Vote Average: \n" + movieVoteAverage2);
        textViewPlotSynopsis.setText("Plot Synopsis: \n" + moviePlotSynopsis2);
    }
}
