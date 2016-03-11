package com.ske.minitrello.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ske.minitrello.R;

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

        //setCustomActionBar();


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addlist:
                Toast.makeText(this, "add list", Toast.LENGTH_SHORT).show();
                return true;

        }

        return true;
    }

    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        LayoutInflater inflater = LayoutInflater.from(this);

//        View customActionBar = inflater.inflate(R.layout.main_toolbar, null);
//        ImageView addButton = (ImageView)customActionBar.findViewById(R.id.add_list_button);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddCardListDialog cardAddDialog = new AddCardListDialog();
//                cardAddDialog.addObserver(MainActivity.this);
//                cardAddDialog.showDialog(MainActivity.this);
//            }
//        });
//
//        actionBar.setCustomView(customActionBar);
//        actionBar.setDisplayShowCustomEnabled(true);
    }



    @Override
    public void update(Observable observable, Object data) {
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pagerAdapter.getCount() - 1);
    }
}
