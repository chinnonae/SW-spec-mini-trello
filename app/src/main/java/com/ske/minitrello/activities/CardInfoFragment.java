package com.ske.minitrello.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardList;



public class CardInfoFragment extends Fragment {

    Card card;
    CardList cardList;

    EditText cardTitle;
    TextView cardDescription;

    public CardInfoFragment(CardList cardList, Card card) {
        this.card = card;
        this.cardList = cardList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_info, container, false);

        cardTitle = (EditText) rootView.findViewById(R.id.card_dialog_title);
        cardDescription = (TextView) rootView.findViewById(R.id.card_dialog_description);

        cardTitle.setText(card.getName());
        cardTitle.setEnabled(false);
        cardDescription.setText(card.getDescription());


        return rootView;
    }


}
