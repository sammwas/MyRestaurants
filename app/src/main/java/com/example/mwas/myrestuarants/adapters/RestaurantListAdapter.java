package com.example.mwas.myrestuarants.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mwas.myrestuarants.Constants;
import com.example.mwas.myrestuarants.R;
import com.example.mwas.myrestuarants.models.Restaurant;
import com.example.mwas.myrestuarants.ui.RestaurantDetailActivity;
import com.example.mwas.myrestuarants.ui.RestaurantDetailFragment;
import com.example.mwas.myrestuarants.util.OnRestaurantSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mwas on 9/12/17.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter <RestaurantListAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants, OnRestaurantSelectedListener restaurantSelectedListener) {
        mContext = context;
        mRestaurants = restaurants;
        mOnRestaurantSelectedListener = restaurantSelectedListener;
    }

    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view,mRestaurants, mOnRestaurantSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.restaurantImageView) ImageView mRestaurantImageView;
        @Bind(R.id.restaurantNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
        private OnRestaurantSelectedListener mRestaurantSelectedListener;

        public RestaurantViewHolder(View itemView, ArrayList<Restaurant> restaurants, OnRestaurantSelectedListener restaurantSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mRestaurants = restaurants;
            mRestaurantSelectedListener = restaurantSelectedListener;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mRestaurantSelectedListener.onRestaurantSelected(itemPosition, mRestaurants, Constants.SOURCE_FIND);

            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
        public void bindRestaurant(Restaurant restaurant) {
            Picasso.with(mContext).load(restaurant.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRestaurantImageView);
            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategories().get(0));
            mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }

        private void createDetailFragment(int position) {
            RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position, Constants.SOURCE_FIND);

            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.restaurantDetailContainer, detailFragment);
            ft.commit();
        }
    }
}
