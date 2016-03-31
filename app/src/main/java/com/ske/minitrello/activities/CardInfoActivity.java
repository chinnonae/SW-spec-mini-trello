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
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.dialogs.AddCommentDialog;
import com.ske.minitrello.dialogs.EditCardDialog;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.views.adapters.CardInfoPagerAdapter;

public class CardInfoActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    int cardPosition;
    Card card;
    CardList cardList;

    ViewPager viewPager;
    TabLayout tabLayout;

    TextView cardTitle;
    FloatingActionButton addFab;
    FloatingActionButton editFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        // Get cards info
        cardPosition = getIntent().getIntExtra("card position", 0);
        cardList = CardKeeper.getInstance().getLists().get(getIntent().getIntExtra("list position", 0));
        card = cardList.getCards().get(cardPosition);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.card_info_toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addFab = (FloatingActionButton) findViewById(R.id.add_fab);
        addFab.setVisibility(addFab.INVISIBLE);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentDialog dialog = new AddCommentDialog(card, CardInfoActivity.this);
                dialog.show();
            }
        });

        editFab = (FloatingActionButton) findViewById(R.id.edit_fab);
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCardDialog dialog = new EditCardDialog(card, CardInfoActivity.this);
                dialog.show();
            }
        });

        cardTitle = (TextView) findViewById(R.id.card_title);
        cardTitle.setText(card.getName());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CardInfoPagerAdapter(getSupportFragmentManager(),
                CardInfoActivity.this, card));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    addFab.hide(true);
                    editFab.show(true);
                }
                if (position == 1) {
                    addFab.show(true);
                    editFab.hide(true);
                }
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

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

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Remove this card?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CardController.removeCard(card, cardPosition);
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
}
