package com.ske.minitrello.utils;


import android.support.v4.view.ViewPager;

import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.views.adapters.CardAdapter;
import com.ske.minitrello.views.adapters.CardListPagerAdapter;

public class CardUtil {

    private static CardAdapter cardAdapter;
    private static ViewPager viewPager;


    public static void setCardAdapter(CardAdapter adapter) {
        cardAdapter = adapter;
    }

    public static void setViewPager(ViewPager pager) {
        viewPager = pager;
    }


    public static void renameCard(Card card, String name) {
        card.setName(name);
        cardAdapter.notifyDataSetChanged();
    }

    public static void renameCardList(CardList cardList, String name) {
        cardList.setName(name);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public static void deleteCard(Card card, int position) {
        CardKeeper.getInstance().deleteCard(card);
        cardAdapter.notifyItemRemoved(position);
    }

    public static void deleteCardList(CardList cardList) {
        CardKeeper.getInstance().deleteCardList(cardList);
    }


}
