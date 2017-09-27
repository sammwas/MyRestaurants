package com.example.mwas.myrestuarants.util;

/**
 * Created by mwas on 9/25/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
