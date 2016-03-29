package com.ske.minitrello.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.utils.CardUtil;
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

    private int listPosition;

    private CardList cardList;
    private List<Card> cards;

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;

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

        cardAdapter = new CardAdapter(cards, new CardClickAction());
        CardUtil.setCardAdapter(cardAdapter);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView cardListTitle = (TextView)rootView.findViewById(R.id.cardlist_title);
        cardListTitle.setText(cardList.getName());

        ImageView addButton = (ImageView)rootView.findViewById(R.id.add_button);
        ImageView deleteButton = (ImageView)rootView.findViewById(R.id.delete_list_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListDialog();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        return rootView;
    }


    @Override
    public void update(Observable observable, Object data) {
        cardAdapter.notifyItemInserted(cards.size() - 1);
        recyclerView.getLayoutManager().scrollToPosition(cards.size() - 1);
    }

    private void showAddListDialog() {
        AddCardDialog acd = new AddCardDialog();
        recyclerView.getLayoutManager().scrollToPosition(cards.size() - 1);
        acd.addObserver(CardListFragment.this);
        acd.showDialog(getActivity(), listPosition);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Message");
        builder.setMessage("Remove this list?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity)getActivity()).delete(cardList);
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    private class CardClickAction implements CardItemClickListener {

        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(getContext(), ShowCardInfoActivity.class);
            intent.putExtra("card position", position);
            intent.putExtra("list position", listPosition);
            startActivity(intent);
        }
    }


}
