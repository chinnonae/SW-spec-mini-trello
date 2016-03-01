package com.ske.minitrello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;

import java.util.List;


public class CardAdapter extends ArrayAdapter<Card> {

    public CardAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.card_list_item, null);
        }

        TextView cardTitle = (TextView) v.findViewById(R.id.card_title);
        TextView cardDescription = (TextView) v.findViewById(R.id.card_description);

        Card card = getItem(position);

        cardTitle.setText(card.getName());
        cardDescription.setText(card.getDescription());

        return v;
    }
}
