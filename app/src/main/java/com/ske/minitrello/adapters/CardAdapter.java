package com.ske.minitrello.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cards;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle;
        TextView cardDescription;

        // each data item is just a string in this case
        public CardViewHolder(View v) {
            super(v);
            cardTitle = (TextView)v.findViewById(R.id.card_title);
            cardDescription = (TextView)v.findViewById(R.id.card_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.cardTitle.setText(cards.get(position).getName());
        holder.cardDescription.setText(cards.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cards.size();
    }
}