package com.ske.minitrello.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.controllers.CommentController;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.Comment;
import com.ske.minitrello.views.adapters.CardItemClickListener;
import com.ske.minitrello.views.adapters.CommentAdapter;
import com.ske.minitrello.views.adapters.CommentItemClickListener;

import java.util.List;

/**
 * Created by Peter on 3/30/2016 AD.
 */
public class CommentFragment extends Fragment {

    Card card;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    public CommentFragment(Card card) {
        this.card = card;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_comment, container, false);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        comments = card.getComments();
        commentAdapter = new CommentAdapter(comments, new ClickAction());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recyclerview);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CommentController.setAdapter(commentAdapter);

        return rootView;
    }

    private class ClickAction implements CommentItemClickListener {

        @Override
        public void onItemClick(View v, int position) {
            Comment comment = card.getComments().get(position);
            showDeleteDialog(comment);
        }
    }

    private void showDeleteDialog(final Comment comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Message");
        builder.setMessage("Remove this comment?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CommentController.removeComment(comment, card);
                        dialog.cancel();
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
