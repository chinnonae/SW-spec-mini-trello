package com.ske.minitrello.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ske.minitrello.R;

import com.ske.minitrello.adapters.CardListPagerAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *  Main page that show list of card lists including the cards inside the lists.
 */

public class MainActivity extends AppCompatActivity implements CardListFragment.OnFragmentInteractionListener, Observer {


    private CardListPagerAdapter pagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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

        List<Card> cards2 = new ArrayList<Card>();
        cards2.addAll(cards);
        List<Card> cards3 = new ArrayList<Card>();
        cards3.addAll(cards);



        CardList cl = new CardList("To-do", cards);

        CardList cl2 = new CardList("Done");
        CardList cl3 = new CardList("Doing", cards2);
        CardList cl4 = new CardList("Another list", cards3);

        addCardList(cl);
        addCardList(cl2);
        addCardList(cl3);
        addCardList(cl4);

    }

    public void addCardList(CardList cardList) {
        CardKeeper.getInstance().addCardList(cardList);
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addlist) {
            AddCardListDialog acld = new AddCardListDialog();
            acld.addObserver(MainActivity.this);
            acld.showDialog(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void update(Observable observable, Object data) {
        pagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(pagerAdapter.getCount()-1);
    }
}
