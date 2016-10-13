package com.abhinavjhanwar.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhinavjhanwar.android.popularmovies.ContentActivity;
import com.abhinavjhanwar.android.popularmovies.R;
import com.abhinavjhanwar.android.popularmovies.utils.PosterDetail;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// Credits: https://www.learn2crack.com/2016/02/recyclerview-json-parsing.html

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private final ArrayList<PosterDetail> poster;
    private final Context context;

    public DataAdapter(Context context, ArrayList<PosterDetail> poster) {
        this.poster = poster;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        final String URL = "http://image.tmdb.org/t/p/w185" + poster.get(i).getPoster_path();
        final String imageURL = "http://image.tmdb.org/t/p/w500" + poster.get(i).getPoster_path();
        final String overView = poster.get(i).getOverview();
        final String title = poster.get(i).getTitle();
        final String releaseDate = poster.get(i).getRelease_date();
        int genreId[] = poster.get(i).getGenre_ids();
        final String genre = getGenre(genreId[0]);
        final float rating = (poster.get(i).getVote_average()) / 2;
        final int id = poster.get(i).getId();

        viewHolder.textView.setText(poster.get(i).getTitle());
        Picasso
                .with(context)
                .load(URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(viewHolder.imageView,
                        //TextView background set according to picasso palette color
                        PicassoPalette.with(URL, viewHolder.imageView)
                                .use(PicassoPalette.Profile.VIBRANT_DARK)
                                .intoBackground(viewHolder.textView, PicassoPalette.Swatch.RGB)
                );

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set values to send to other activity and start it when cardview is clicked
                Bundle bundle = new Bundle();
                bundle.putFloat("rating", rating);
                bundle.putInt("id", id);
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("overview", overView);
                intent.putExtra("poster", imageURL);
                intent.putExtra("title", title);
                intent.putExtra("release-date", releaseDate);
                intent.putExtra("genre", genre);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poster.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.grid_item_movie_name)
        TextView textView;
        @BindView(R.id.grid_item_movie_poster)
        ImageView imageView;
        @BindView(R.id.grid_item_movie_cardview)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // Genre list from http://api.themoviedb.org/3/genre/movie/list?api_key=<YOUR_API_KEY>
    private String getGenre(int genreID) {
        String genre = "Action";

        switch (genreID) {
            case 28:
                genre = "Action";
                break;
            case 12:
                genre = "Adventure";
                break;
            case 16:
                genre = "Animation";
                break;
            case 35:
                genre = "Comedy";
                break;
            case 80:
                genre = "Crime";
                break;
            case 99:
                genre = "Documentary";
                break;
            case 18:
                genre = "Drama";
                break;
            case 10751:
                genre = "Family";
                break;
            case 14:
                genre = "Fantasy";
                break;
            case 10769:
                genre = "Foreign";
                break;
            case 36:
                genre = "History";
                break;
            case 27:
                genre = "Horror";
                break;
            case 10402:
                genre = "Music";
                break;
            case 9648:
                genre = "Mystery";
                break;
            case 10749:
                genre = "Romance";
                break;
            case 878:
                genre = "Science Fiction";
                break;
            case 10770:
                genre = "TV Movie";
                break;
            case 53:
                genre = "Thriller";
                break;
            case 10752:
                genre = "War";
                break;
            case 37:
                genre = "Western";
                break;
        }
        return genre;
    }
}