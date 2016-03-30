package com.ske.minitrello.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ske.minitrello.activities.CardListFragment;
import com.ske.minitrello.models.CardKeeper;

import java.util.HashMap;
import java.util.Map;

public class CardListPagerAdapter extends FragmentStatePagerAdapter {

    private Map<Integer, Fragment> fragmentMap;

    public CardListPagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentMap = new HashMap<>();
    }

    public void removeItem(Fragment fragment) {
        fragmentMap.remove(fragment);
    }

    @Override
    public int getItemPosition(Object object) {
//        Set<Integer> keys = fragmentMap.keySet();
//        if (fragmentMap.containsValue((Fragment)object))
//            return fragmentMap.
//        if (fragmentMap.contains((Fragment) object) )
//            return fragmentMap.indexOf((Fragment) object);

        return POSITION_NONE;

    }

    @Override
    public Fragment getItem(int position) {

        if (position >= fragmentMap.size()) {
            CardListFragment fragment = CardListFragment.newInstance(position);
            fragmentMap.put(position, fragment);
            return fragment;
        }

//        return CardListFragment.newInstance(position);

        return fragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return CardKeeper.getInstance().getSize();
    }


}
