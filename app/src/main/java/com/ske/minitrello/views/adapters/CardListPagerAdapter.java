package com.ske.minitrello.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.ske.minitrello.activities.CardListFragment;
import com.ske.minitrello.models.CardKeeper;

import java.util.ArrayList;
import java.util.List;


public class CardListPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public CardListPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    public void removeItem(Fragment fragment) {
        fragmentList.remove(fragment);
    }

    @Override
    public int getItemPosition(Object object) {
        if (fragmentList.contains((Fragment) object) )
            return fragmentList.indexOf((Fragment) object);

        return POSITION_NONE;

    }

    @Override
    public Fragment getItem(int position) {

        if (position >= fragmentList.size()) {
            Fragment fragment = CardListFragment.newInstance(position);
            fragmentList.add(fragment);
            return fragment;
        }

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return CardKeeper.getInstance().getSize();
    }


}
