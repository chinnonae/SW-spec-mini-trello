package com.ske.minitrello.controllers;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ske.minitrello.activities.CardListFragment;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.views.adapters.CardAdapter;
import com.ske.minitrello.views.adapters.CardListPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Supports various interaction of cards, also updates the views.
 */
public class CardController {

    private static CardAdapter cardAdapter;
    private static RecyclerView recyclerView;

    private static ViewPager viewPager;
    private static CardListPagerAdapter pagerAdapter;


    public static void setCardAdapter(CardAdapter adapter) {
        Log.i("Set adapter" , "Set " + adapter.hashCode());
        cardAdapter = adapter;
    }

    public static void setRecyclerView(RecyclerView rv) {
        Log.i("Set rv", "Set " + rv.hashCode());
        recyclerView = rv;
    }

    public static void setViewPager(ViewPager pager) {
        viewPager = pager;
    }

    public static void setPagerAdapter(CardListPagerAdapter adapter) {
        pagerAdapter = adapter;
    }


    public static void addCard(Card card, CardList cardList) {
        int position = CardKeeper.getInstance().getLists().size() - 1;
        CardKeeper.getInstance().addCardToCardList(card, cardList);
        cardAdapter.notifyItemInserted(position);
        recyclerView.scrollToPosition(position);
    }

    public static void addCardList(CardList cardList) {
        CardKeeper.getInstance().addCardList(cardList);
        CardListPagerAdapter adapter = (CardListPagerAdapter)viewPager.getAdapter();
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(adapter.getCount() - 1);
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
        cardAdapter.notifyItemRemoved(position);
    }

    public static void deleteCardList(CardList cardList) {
        int position = CardKeeper.getInstance().getLists().indexOf(cardList);
        CardKeeper.getInstance().deleteCardList(cardList);
        CardListFragment fragment = (CardListFragment)pagerAdapter.getItem(viewPager.getCurrentItem());
        pagerAdapter.removeItem(fragment);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position - 1);
    }


}
