package com.ske.minitrello.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CardAdapter;
import com.ske.minitrello.adapters.CardItemClickListener;
import com.ske.minitrello.adapters.CommentAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.Comment;

import java.util.List;
import java.util.Observable;


public class CardInfoDialog extends Observable {

    EditText cardTitle;
    TextView cardDescription;
    ImageView editTitleBtn;
    ImageView editDescBtn;
    ImageView addCommentBtn;
    ImageView closeBtn;

    boolean isEditingTitle = false;
    boolean isEditingDescription = false;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    Card card;
    List<Comment> comments;

    public CardInfoDialog(Card card) {
        this.card = card;

    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_card_info);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        recyclerView = (RecyclerView) dialog.findViewById(R.id.comment_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(activity);

        comments = card.getComments();
        commentAdapter = new CommentAdapter(comments);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cardTitle = (EditText) dialog.findViewById(R.id.card_dialog_title);
        cardDescription = (TextView) dialog.findViewById(R.id.card_dialog_description);
        editTitleBtn = (ImageView) dialog.findViewById(R.id.renameButton);

        cardTitle.setFocusableInTouchMode(false);
        cardTitle.setFocusable(false);
        cardTitle.setCursorVisible(false);
        cardTitle.setBackgroundColor(Color.TRANSPARENT);

        cardTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCardName();
            }
        });

        closeBtn = (ImageView) dialog.findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addCommentBtn = (ImageView) dialog.findViewById(R.id.add_comment_button);
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

        dialog.show();

    }

    private void editCardName() {
        if (!isEditingTitle) {
            editTitleBtn.setImageResource(R.drawable.correct);
            cardTitle.setFocusableInTouchMode(true);
            cardTitle.setFocusable(true);
            cardTitle.setCursorVisible(true);
            cardTitle.requestFocus();
            isEditingTitle = true;
        }
        else {
            editTitleBtn.setImageResource(R.drawable.edit_black);
            cardTitle.setFocusableInTouchMode(false);
            cardTitle.setFocusable(false);
            cardTitle.setCursorVisible(false);
            card.setName(cardTitle.getText().toString());
            isEditingTitle = false;
        }
    }
}
