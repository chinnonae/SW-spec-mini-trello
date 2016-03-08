package com.ske.minitrello.adapters;

import android.view.View;

import com.ske.minitrello.models.Card;


public interface CardItemClickListener {
    public void onItemClick(View v, Card card);
}