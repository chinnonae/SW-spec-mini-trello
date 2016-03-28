package com.ske.minitrello.views.adapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.activities.ShowCardInfoActivity;
import com.ske.minitrello.models.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Card> cards;
    private CardItemClickListener listener;

    public CardAdapter(List<Card> cards, CardItemClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    public void remove(int position){
        cards.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle;

        public ViewHolder(View v) {
            super(v);
            cardTitle = (TextView)v.findViewById(R.id.card_title);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, vh.getAdapterPosition());
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardTitle.setText(cards.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}