package com.ske.minitrello.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ske.minitrello.activities.CardListFragment;
import com.ske.minitrello.models.CardKeeper;


public class CardListPagerAdapter extends FragmentStatePagerAdapter {

    public CardListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CardListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return CardKeeper.getInstance().getSize();
    }
}
