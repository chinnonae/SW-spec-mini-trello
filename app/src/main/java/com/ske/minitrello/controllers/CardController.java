package com.ske.minitrello.controllers;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ske.minitrello.activities.CardListFragment;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.views.adapters.CardAdapter;
import com.ske.minitrello.views.adapters.CardListPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles various interaction of cards, also updates the views associated with it.
 */
public class CardController {

    static String className = "CardController";

    private static CardAdapter cardAdapter;
    private static RecyclerView recyclerView;

    private static ViewPager viewPager;
    private static CardListPagerAdapter pagerAdapter;


    public static void setCardAdapter(CardAdapter adapter) {
        Log.i(className , "Set " + adapter.toString());
        cardAdapter = adapter;
    }

    public static void setRecyclerView(RecyclerView rv) {
        Log.i(className, "Set " + rv.toString());
        recyclerView = rv;
    }

    public static int getCurrentPage() {
        Log.i(className, viewPager.getCurrentItem() + "= NUMBER OF CURRENT PAGE");
        return viewPager.getCurrentItem();
    }

    public static void setViewPager(ViewPager pager) {
        viewPager = pager;
    }

    public static void setPagerAdapter(CardListPagerAdapter adapter) {
        pagerAdapter = adapter;
    }


    public static void addCard(Card card, CardList cardList) {

        CardKeeper.getInstance().addCardToCardList(card, cardList);

        int position = cardList.getCards().size() - 1;
        recyclerView.getLayoutManager().scrollToPosition(position);
        Log.i(className, "Inserted via adapter - " + cardAdapter.toString());
        cardAdapter.notifyItemInserted(position);

    }

    public static void addCardList(CardList cardList) {
        CardKeeper.getInstance().addCardList(cardList);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pagerAdapter.getCount() - 1);
    }

    public static void renameCard(Card card, String name) {
        CardKeeper.getInstance().renameCard(card, name);
        cardAdapter.notifyDataSetChanged();
    }

    public static void renameCardList(CardList cardList, String name) {
        CardKeeper.getInstance().renameCardList(cardList, name);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public static void deleteCard(Card card, int position) {
        CardKeeper.getInstance().deleteCard(card);
        Log.i(className, "Removed via adapter - " + cardAdapter.toString());
        cardAdapter.notifyItemRemoved(position);
    }

    public static void deleteCardList(CardList cardList) {
        int position = CardKeeper.getInstance().getLists().indexOf(cardList);
        CardKeeper.getInstance().deleteCardList(cardList);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position - 1);
    }


}
