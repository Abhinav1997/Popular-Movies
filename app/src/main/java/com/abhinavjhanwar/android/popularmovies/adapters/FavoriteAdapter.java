package com.abhinavjhanwar.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhinavjhanwar.android.popularmovies.ContentActivity;
import com.abhinavjhanwar.android.popularmovies.R;
import com.abhinavjhanwar.android.popularmovies.provider.FavoriteProvider;
import com.github.florent37.picassopalette.PicassoPalette;

import java.io.ByteArrayInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final Context context;
    private Cursor cursor;

    public FavoriteAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.ViewHolder viewHolder, int i) {

        if (!cursor.isAfterLast()) {

            final String posterID;
            final String overView;
            final String title;
            final String releaseDate;
            final String genre;
            final byte[] poster_image;
            final Bitmap grid_image;
            final float rating;
            final int id;
            posterID = cursor.getString(cursor.getColumnIndex(FavoriteProvider.COL_POSTER_ID));
            overView = cursor.getString(cursor.getColumnIndex(FavoriteProvider.COL_OVERVIEW));
            title = cursor.getString(cursor.getColumnIndex(FavoriteProvider.COL_TITLE));
            releaseDate = cursor.getString(cursor.getColumnIndex(FavoriteProvider.COL_RELEASE_DATE));
            genre = cursor.getString(cursor.getColumnIndex(FavoriteProvider.COL_GENRE));
            rating = cursor.getFloat(cursor.getColumnIndex(FavoriteProvider.COL_RATING));
            id = cursor.getInt(cursor.getColumnIndex(FavoriteProvider.COL_ID));
            poster_image = cursor.getBlob(cursor.getColumnIndex(FavoriteProvider.COL_POSTER_IMAGE));
            grid_image = byteToBitmap(cursor.getBlob(cursor.getColumnIndex(FavoriteProvider.COL_GRID_IMAGE)));

            Palette.from(grid_image).maximumColorCount(32).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    // Get the "vibrant" color swatch based on the bitmap
                    Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                    if (vibrant != null) {
                        // Set the background color of a layout based on the vibrant color
                        viewHolder.textView.setBackgroundColor(vibrant.getRgb());
                    }
                }
            });

            viewHolder.imageView.setImageBitmap(grid_image);
            viewHolder.textView.setText(title);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Set values to send to other activity and start it when cardview is clicked
                    Bundle bundle = new Bundle();
                    bundle.putFloat("rating", rating);
                    bundle.putInt("id", id);
                    Intent intent = new Intent(context, ContentActivity.class);
                    intent.putExtra("overview", overView);
                    intent.putExtra("poster", posterID);
                    intent.putExtra("title", title);
                    intent.putExtra("release-date", releaseDate);
                    intent.putExtra("genre", genre);
                    intent.putExtra("poster_image", poster_image);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            cursor.moveToNext();
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public Bitmap byteToBitmap(byte[] photo) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
        return BitmapFactory.decodeStream(imageStream);
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

}