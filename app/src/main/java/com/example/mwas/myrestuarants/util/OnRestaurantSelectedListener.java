package com.example.mwas.myrestuarants.util;

import com.example.mwas.myrestuarants.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by mwas on 9/28/17.
 */

public interface OnRestaurantSelectedListener {
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source);
}
