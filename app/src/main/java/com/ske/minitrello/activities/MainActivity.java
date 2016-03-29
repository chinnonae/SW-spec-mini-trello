package com.ske.minitrello.activities;


import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import com.ske.minitrello.R;

import com.ske.minitrello.models.CardList;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.views.adapters.CardAdapter;
import com.ske.minitrello.views.adapters.CardListPagerAdapter;
import com.ske.minitrello.dialogs.AddCardListDialog;
import com.ske.minitrello.models.CardKeeper;

/**
 *  Main page that show list of card lists including the cards inside the lists.
 */

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {


    private CardListPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        // Inject this MainActivity to CardKeeper
        CardKeeper ck = CardKeeper.getInstance(this);

        pagerAdapter = new CardListPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());
        CardController.setViewPager(viewPager);
        CardController.setPagerAdapter(pagerAdapter);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addlist:
                AddCardListDialog cardAddDialog = new AddCardListDialog();
                cardAddDialog.showDialog(MainActivity.this);
                return true;

        }
        return true;
    }

//    public void delete(CardList cardList) {
//        int position = CardKeeper.getInstance().getLists().indexOf(cardList);
//        CardKeeper.getInstance().deleteCardList(cardList);
//        CardListFragment fragment = (CardListFragment)pagerAdapter.getItem(viewPager.getCurrentItem());
////        pagerAdapter = new CardListPagerAdapter(getSupportFragmentManager());
//        pagerAdapter.removeItem(fragment);
//        pagerAdapter.notifyDataSetChanged();
////        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(position - 1);

    //}

    private class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            Log.e("Position", "Current Pos = " + position);
            CardListFragment fragment = (CardListFragment) pagerAdapter.getItem(position);
            fragment.bindToController();
        }
    }


}
