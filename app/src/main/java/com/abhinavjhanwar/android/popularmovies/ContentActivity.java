package com.abhinavjhanwar.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abhinavjhanwar.android.popularmovies.adapters.ReviewAdapter;
import com.abhinavjhanwar.android.popularmovies.adapters.TrailerAdapter;
import com.abhinavjhanwar.android.popularmovies.api.MovieAPI;
import com.abhinavjhanwar.android.popularmovies.utils.ReviewDetail;
import com.abhinavjhanwar.android.popularmovies.utils.ReviewResponse;
import com.abhinavjhanwar.android.popularmovies.utils.TrailerDetail;
import com.abhinavjhanwar.android.popularmovies.utils.TrailerResponse;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @BindView(R.id.trailer_rv)
    RecyclerView trailerRv;
    @BindView(R.id.trailerProgressBar)
    ProgressBar trailerProgressBar;
    @BindView(R.id.review_rv)
    RecyclerView reviewRv;
    @BindView(R.id.reviewProgressBar)
    ProgressBar reviewProgressBar;

    private String overView;
    private String posterURL;
    private String title;
    private String movieRelease;
    private String genre;
    private float rating = (float) 2.5;
    private int id;

    private ArrayList<TrailerDetail> trailerData;
    private TrailerAdapter trailerAdapter;
    private ArrayList<ReviewDetail> reviewData;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
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
        id = bundle.getInt("id");

        initViews();
        loadTrailers();
        loadReviews();
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

    public void loadTrailers() {
        String baseURL = "http://api.themoviedb.org";

        // Use retrofit for loading URL and getting JSON
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get JSON values, credits: https://www.learn2crack.com/2016/02/recyclerview-json-parsing.html
        Call<TrailerResponse> call;
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        call = movieAPI.getTrailers(Integer.toString(id), BuildConfig.MOVIE_DB_API_KEY);

        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                TrailerResponse trailerResponse = response.body();
                trailerData = new ArrayList<>(Arrays.asList(trailerResponse.getResults()));
                // Build adapter based on json entries
                trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerData);
                trailerRv.setVisibility(View.GONE);
                if (trailerAdapter != null) {
                    //Hide progressbar and then show recyclerview after it's loaded
                    trailerRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
                    trailerRv.setAdapter(trailerAdapter);
                    trailerProgressBar.setVisibility(View.GONE);
                    trailerRv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void loadReviews() {
        String baseURL = "http://api.themoviedb.org";

        // Use retrofit for loading URL and getting JSON
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get JSON values, credits: https://www.learn2crack.com/2016/02/recyclerview-json-parsing.html
        Call<ReviewResponse> call;
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        call = movieAPI.getReviews(Integer.toString(id), BuildConfig.MOVIE_DB_API_KEY);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                reviewData = new ArrayList<>(Arrays.asList(reviewResponse.getResults()));
                // Build adapter based on json entries
                reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewData);
                reviewRv.setVisibility(View.GONE);
                if (reviewAdapter != null) {
                    //Hide progressbar and then show recyclerview after it's loaded
                    reviewRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
                    reviewRv.setAdapter(reviewAdapter);
                    reviewProgressBar.setVisibility(View.GONE);
                    reviewRv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}