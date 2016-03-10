package com.ske.minitrello.views.adapters;

import android.view.View;

import com.ske.minitrello.models.Card;


public interface CardItemClickListener {
    public void onItemClick(View v, int position);
}