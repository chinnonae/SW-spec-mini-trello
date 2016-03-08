package com.ske.minitrello.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CommentAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
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

    Card card;
    List<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_info);

        card = (Card)getIntent().getSerializableExtra("card");

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
}
