package com.ske.minitrello.activities;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.views.adapters.CardInfoPagerAdapter;

public class CardInfoActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    int cardPosition;
    Card card;
    CardList cardList;

    EditText cardTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        cardPosition = getIntent().getIntExtra("card position", 0);
        cardList = CardKeeper.getInstance().getLists().get(getIntent().getIntExtra("list position", 0));
        card = cardList.getCards().get(cardPosition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.card_info_toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardTitle = (EditText) findViewById(R.id.card_title);
        cardTitle.setText(card.getName());

        //TODO View pager showing 2 fragments

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CardInfoPagerAdapter(getSupportFragmentManager(),
                CardInfoActivity.this, card));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Remove this card?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CardController.deleteCard(card, cardPosition);
                        dialog.cancel();
                        finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_card:
                showDeleteDialog();
                return true;

        }

        return true;
    }
}
