package com.ske.minitrello.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ske.minitrello.activities.CardListFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {


    private int count;

    public PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return new CardListFragment();
    }

    @Override
    public int getCount() {
        return count;
    }
}
