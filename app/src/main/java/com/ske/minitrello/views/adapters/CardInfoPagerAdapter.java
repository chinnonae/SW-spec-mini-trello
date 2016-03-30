package com.ske.minitrello.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ske.minitrello.activities.CommentFragment;
import com.ske.minitrello.activities.DescriptionFragment;
import com.ske.minitrello.models.Card;

public class CardInfoPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Description", "Comments" };
    private Context context;
    private Card card;

    public CardInfoPagerAdapter(FragmentManager fm, Context context, Card card ) {
        super(fm);
        this.context = context;
        this.card = card;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new DescriptionFragment(card);
        return new CommentFragment(card);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}