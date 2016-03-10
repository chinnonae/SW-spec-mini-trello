package com.ske.minitrello.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.views.adapters.CardAdapter;
import com.ske.minitrello.views.adapters.CardItemClickListener;
import com.ske.minitrello.dialogs.AddCardDialog;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CardListFragment extends Fragment implements Observer {

    private CardList cardList;
    private List<Card> cards;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private int listPosition;
    Context context;

    public CardListFragment() {
    }

    public static CardListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        CardListFragment fragment = new CardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listPosition = getArguments().getInt("position");
        cardList = CardKeeper.getInstance().getLists().get(listPosition);
        cards = cardList.getCards();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_card_list, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        context = getContext();

        cardAdapter = new CardAdapter(cards, new ClickAction());


        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView cardListTitle = (TextView)rootView.findViewById(R.id.cardlist_title);
        cardListTitle.setText(cardList.getName());

        ImageView addButton = (ImageView)rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getArguments().getInt("position");
                AddCardDialog acd = new AddCardDialog();
                recyclerView.getLayoutManager().scrollToPosition(cards.size() - 1);
                acd.addObserver(CardListFragment.this);
                acd.showDialog(getActivity(), position);
            }
        });

        return rootView;
    }


    @Override
    public void update(Observable observable, Object data) {
        cardAdapter.notifyItemInserted(cards.size() - 1);
        recyclerView.getLayoutManager().scrollToPosition(cards.size() - 1);
    }

    class ClickAction implements CardItemClickListener{
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(context, ShowCardInfoActivity.class);
            intent.putExtra("card position", position);
            intent.putExtra("list position", listPosition);
            startActivity(intent);
        }
    }


}
