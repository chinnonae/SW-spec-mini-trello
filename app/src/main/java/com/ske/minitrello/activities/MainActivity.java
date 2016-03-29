package com.ske.minitrello.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import com.ske.minitrello.R;

import com.ske.minitrello.models.CardList;
import com.ske.minitrello.utils.CardUtil;
import com.ske.minitrello.views.adapters.CardItemClickListener;
import com.ske.minitrello.views.adapters.CardListPagerAdapter;
import com.ske.minitrello.dialogs.AddCardListDialog;
import com.ske.minitrello.models.CardKeeper;

import java.util.Observable;
import java.util.Observer;

/**
 *  Main page that show list of card lists including the cards inside the lists.
 */

public class MainActivity extends AppCompatActivity implements Observer, Toolbar.OnMenuItemClickListener {


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
        CardUtil.setViewPager(viewPager);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addlist:
                AddCardListDialog cardAddDialog = new AddCardListDialog();
                cardAddDialog.addObserver(MainActivity.this);
                cardAddDialog.showDialog(MainActivity.this);
                return true;

        }
        return true;
    }

    public void delete(CardList cardList) {
        int position = CardKeeper.getInstance().getLists().indexOf(cardList);
        CardKeeper.getInstance().deleteCardList(cardList);
        pagerAdapter = new CardListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position - 1);
    }

    @Override
    public void update(Observable observable, Object data) {
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pagerAdapter.getCount() - 1);
    }


}
