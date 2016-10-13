package com.abhinavjhanwar.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhinavjhanwar.android.popularmovies.R;
import com.abhinavjhanwar.android.popularmovies.utils.ReviewDetail;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final ArrayList<ReviewDetail> review;
    private final Context context;

    public ReviewAdapter(Context context, ArrayList<ReviewDetail> review) {
        this.review = review;
        this.context = context;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.authorName.setText(review.get(i).getAuthor());
        viewHolder.reviewContent.setText(review.get(i).getContent());
     /*Collapsible cardview code
        viewHolder.reviewContent.setVisibility(View.GONE);

        viewHolder.reviewCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (viewHolder.arrowKey.getText().toString().equals("\u25bc")) {
                    viewHolder.arrowKey.setText("\u25b2");
                    viewHolder.reviewContent.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.reviewContent.setVisibility(View.GONE);
                    viewHolder.arrowKey.setText("\u25bc");
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return review.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.author_name)
        TextView authorName;
        @BindView(R.id.review_content)
        TextView reviewContent;
/* Collapsible cardview code
        @BindView(R.id.arrow)
        TextView arrowKey;
        @BindView(R.id.item_review_cardview)
        CardView reviewCard;
*/

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}