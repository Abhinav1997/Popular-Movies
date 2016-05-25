package com.abhinavjhanwar.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity {

    @BindView(R.id.expand_text_view)
    ExpandableTextView overviewText;
    @BindView(R.id.content_image)
    ImageView movieImage;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.genre)
    TextView movieGenre;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String overView;
    private String posterURL;
    private String title;
    private String movieRelease;
    private String genre;
    private float rating = (float) 2.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get intent values passed from data adapter
        overView = getIntent().getStringExtra("overview");
        posterURL = getIntent().getStringExtra("poster");
        title = getIntent().getStringExtra("title");
        movieRelease = getIntent().getStringExtra("release-date");
        genre = getIntent().getStringExtra("genre");
        Bundle bundle = getIntent().getExtras();
        rating = bundle.getFloat("rating");

        initViews();
    }

    private void initViews() {
        // Set values to content layout
        movieTitle.setText(title);
        overviewText.setText(overView);
        Picasso
                .with(getApplicationContext())
                .load(posterURL)
                .placeholder(R.drawable.placeholder_content)
                .error(R.drawable.placeholder_content_error)
                .into(movieImage);
        releaseDate.setText(movieRelease);
        movieGenre.setText(genre);
        ratingBar.setRating(rating);
    }
}
