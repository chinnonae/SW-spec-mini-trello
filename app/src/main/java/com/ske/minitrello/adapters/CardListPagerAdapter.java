package com.ske.minitrello.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ske.minitrello.activities.CardListFragment;


public class CardListPagerAdapter extends FragmentStatePagerAdapter {

    public CardListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CardListFragment.newInstance(position + 1 + "");
    }

    @Override
    public int getCount() {
        return 3;
    }
}
