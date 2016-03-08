package com.ske.minitrello.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CommentAdapter;
import com.ske.minitrello.dialogs.AddCardListDialog;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.models.Comment;

import java.util.List;

public class ShowCardInfoActivity extends AppCompatActivity {

    EditText cardTitle;
    TextView cardDescription;
    ImageView editTitleBtn;
    ImageView editDescBtn;
    ImageView addCommentBtn;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    CardList cardList;
    Card card;
    List<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_info);

        setCustomActionBar();

        card = (Card)getIntent().getSerializableExtra("card");
        cardList = (CardList)getIntent().getSerializableExtra("card list");

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        comments = card.getComments();
        commentAdapter = new CommentAdapter(comments);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cardTitle = (EditText) findViewById(R.id.card_dialog_title);
        cardDescription = (TextView) findViewById(R.id.card_dialog_description);
        editTitleBtn = (ImageView) findViewById(R.id.renameButton);

        cardTitle.setFocusableInTouchMode(false);
        cardTitle.setFocusable(false);
        cardTitle.setCursorVisible(false);
        cardTitle.setBackgroundColor(Color.TRANSPARENT);

        addCommentBtn = (ImageView) findViewById(R.id.add_comment_button);
        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment("New comment");
                card.addComment(comment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
            }
        });

        cardTitle.setText(card.getName());
        cardDescription.setText(card.getDescription());


    }

    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View customActionBar = inflater.inflate(R.layout.card_view_actionbar, null);
        ImageView newCardList_img = (ImageView)customActionBar.findViewById(R.id.new_card_list_img);
        newCardList_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    public void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this card?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO implement card removal
                        cardList.removeCard(card);
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
}
