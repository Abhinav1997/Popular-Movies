package com.abhinavjhanwar.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhinavjhanwar.android.popularmovies.adapters.ReviewAdapter;
import com.abhinavjhanwar.android.popularmovies.adapters.TrailerAdapter;
import com.abhinavjhanwar.android.popularmovies.api.MovieAPI;
import com.abhinavjhanwar.android.popularmovies.provider.FavoriteProvider;
import com.abhinavjhanwar.android.popularmovies.utils.ReviewDetail;
import com.abhinavjhanwar.android.popularmovies.utils.ReviewResponse;
import com.abhinavjhanwar.android.popularmovies.utils.TrailerDetail;
import com.abhinavjhanwar.android.popularmovies.utils.TrailerResponse;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @BindView(R.id.favfab)
    FloatingActionButton favFab;
    @BindView(R.id.parent_layout_content)
    CoordinatorLayout coordinatorLayout;

    private String overView;
    private String posterURL;
    private String gridPosterURL;
    private String title;
    private String movieRelease;
    private String genre;
    private String posterID;
    private float rating = (float) 2.5;
    private int id;

    private ArrayList<TrailerDetail> trailerData;
    private TrailerAdapter trailerAdapter;
    private ArrayList<ReviewDetail> reviewData;
    private ReviewAdapter reviewAdapter;

    private Uri table = FavoriteProvider.FAVORITES;

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

        favFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (favFab.getDrawable().getConstantState() == getFabDrawable(R.drawable.ic_favorite_black).getConstantState()) {
                    favFab.setImageDrawable(getFabDrawable(R.drawable.ic_favorite_white));
                    Snackbar.make(coordinatorLayout, getResources().getString(R.string.action_add_fav), Snackbar.LENGTH_SHORT).show();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(Integer.toString(id), id).apply();
                    addFav();
                } else {
                    favFab.setImageDrawable(getFabDrawable(R.drawable.ic_favorite_black));
                    Snackbar.make(coordinatorLayout, getResources().getString(R.string.action_remove_fav), Snackbar.LENGTH_SHORT).show();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove(Integer.toString(id)).apply();
                    getContentResolver().delete(table, FavoriteProvider.COL_ID + "=?",  new String[]{Integer.toString(id)});
                }
            }
        });

        // Get intent values passed from data adapter
        overView = getIntent().getStringExtra("overview");
        posterID = getIntent().getStringExtra("poster");
        title = getIntent().getStringExtra("title");
        movieRelease = getIntent().getStringExtra("release-date");
        genre = getIntent().getStringExtra("genre");
        Bundle bundle = getIntent().getExtras();
        rating = bundle.getFloat("rating");
        id = bundle.getInt("id");
        posterURL = "http://image.tmdb.org/t/p/w500" + posterID;
        gridPosterURL = "http://image.tmdb.org/t/p/w185" + posterID;

        initViews();
        loadTrailers();
        loadReviews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sharetrailer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.shareButton) {
            shareTrailer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        // Set values to content layout
        movieTitle.setText(title);
        overviewText.setText(overView);
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SORT", "popular").equals("favorite")) {
            movieImage.setImageBitmap(byteToBitmap(getIntent().getByteArrayExtra("poster_image")));
        } else {
            Picasso
                    .with(getApplicationContext())
                    .load(posterURL)
                    .placeholder(R.drawable.placeholder_content)
                    .error(R.drawable.placeholder_content_error)
                    .into(movieImage);
        }
        releaseDate.setText(movieRelease);
        movieGenre.setText(genre);
        ratingBar.setRating(rating);

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).contains(Integer.toString(id))) {
            favFab.setImageDrawable(getFabDrawable(R.drawable.ic_favorite_white));
        }

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

    public Drawable getFabDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(id, getApplicationContext().getTheme());
        } else {
            return getResources().getDrawable(id);
        }
    }

    private void shareTrailer() {
        if (trailerData != null) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("title") + " Trailer");
            share.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + trailerData.get(0).getKey());
            startActivity(Intent.createChooser(share, "Share YouTube trailer"));
        } else {
            Toast.makeText(getApplicationContext(), "Trailers not yet loaded", Toast.LENGTH_SHORT).show();
        }
    }

    public void addFav() {
        ContentValues values = new ContentValues();
        values.put(FavoriteProvider.COL_ID, id);
        values.put(FavoriteProvider.COL_TITLE, title);
        values.put(FavoriteProvider.COL_OVERVIEW, overView);
        values.put(FavoriteProvider.COL_POSTER_ID, posterID);
        values.put(FavoriteProvider.COL_GENRE, genre);
        values.put(FavoriteProvider.COL_RATING, rating);
        values.put(FavoriteProvider.COL_RELEASE_DATE, movieRelease);
        values.put(FavoriteProvider.COL_POSTER_IMAGE, getByteFromURL(posterURL));
        values.put(FavoriteProvider.COL_GRID_IMAGE, getByteFromURL(gridPosterURL));
        getContentResolver().insert(table, values);
    }

    public static byte[] getByteFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap.recycle();
            byte[] urlByte = stream.toByteArray();
            stream.close();
            return urlByte;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap byteToBitmap(byte[] photo) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
        return BitmapFactory.decodeStream(imageStream);
    }
}