package com.ske.minitrello.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ske.minitrello.R;

import com.ske.minitrello.adapters.CardListPagerAdapter;
import com.ske.minitrello.dialogs.AddCardListDialog;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *  Main page that show list of card lists including the cards inside the lists.
 */

public class MainActivity extends AppCompatActivity implements Observer {

    private CardListPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardKeeper ck = CardKeeper.getInstance(this);

        pagerAdapter = new CardListPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_addlist) {
            AddCardListDialog cardAddDialog = new AddCardListDialog();
            cardAddDialog.addObserver(MainActivity.this);
            cardAddDialog.showDialog(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d("MainActivity", String.format("MainActivity notified, pagerAdapterCount: %d", pagerAdapter.getCount()));

        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pagerAdapter.getCount() - 1);
    }
}
