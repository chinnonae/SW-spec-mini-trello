package com.ske.minitrello.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CardListPagerAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main page that show list of card lists including the cards inside the lists.
 */

public class MainActivity extends AppCompatActivity implements CardListFragment.OnFragmentInteractionListener {


    private CardListPagerAdapter pagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        pagerAdapter = new CardListPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);

        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card("Title1", "Description"));
        cards.add(new Card("Title2", "Description"));
        cards.add(new Card("Title3", "Description"));
        cards.add(new Card("Title4", "Description"));
        cards.add(new Card("Title5", "Description"));
        cards.add(new Card("Title6", "Description"));
        cards.add(new Card("Title7", "Description"));
        cards.add(new Card("Title8", "Description"));
        cards.add(new Card("Title9", "Description"));

        CardList cl = new CardList("To-do", cards);
        CardList cl2 = new CardList("Done", cards);
        CardList cl3 = new CardList("Doing", cards);
        CardList cl4 = new CardList("Another list", cards);

        CardKeeper.getInstance().addCardList(cl);
        CardKeeper.getInstance().addCardList(cl2);
        CardKeeper.getInstance().addCardList(cl3);
        CardKeeper.getInstance().addCardList(cl4);
        pagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}
