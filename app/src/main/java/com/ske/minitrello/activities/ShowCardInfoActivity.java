package com.ske.minitrello.activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ske.minitrello.R;
import com.ske.minitrello.dialogs.AddCardListDialog;
import com.ske.minitrello.views.adapters.CommentAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.models.Comment;

import java.util.List;

public class ShowCardInfoActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    CardList cardList;
    Card card;
    List<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cardview_toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardList = CardKeeper.getInstance().getLists().get(getIntent().getIntExtra("list position", 0));
        card = cardList.getCards().get(getIntent().getIntExtra("card position", 0));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CardInfoFragment(cardList, card))
                    .commit();
        }


    }


    public void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Remove this card?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO implement card removal
                        CardKeeper.getInstance().deleteCard(card);
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

        AlertDialog alert11 = builder.create();
        alert11.show();
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
