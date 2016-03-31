package com.ske.minitrello.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;


public class DescriptionFragment extends Fragment {

    Card card;
    TextView cardDescription;

    public DescriptionFragment(Card card) {
        this.card = card;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_description, container, false);

        cardDescription = (TextView) rootView.findViewById(R.id.card_info_description);

        cardDescription.setText(card.getDescription());

        return rootView;
    }


}
