package com.ske.minitrello.controllers;

import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.Comment;
import com.ske.minitrello.views.adapters.CommentAdapter;

/**
 * Created by Peter on 3/31/2016 AD.
 */
public class CommentController {

    static CommentAdapter adapter;

    public static void setAdapter(CommentAdapter commentAdapter) {
        adapter = commentAdapter;
    }

    public static void addComment(Comment comment, Card card) {
        int position = card.getComments().size();
        CardKeeper.getInstance().addCommentToCard(comment, card);
        adapter.notifyItemInserted(position);
    }

    public static void removeComment(Comment comment, Card card) {
        int position = card.getComments().indexOf(comment);
        CardKeeper.getInstance().deleteComment(comment);
        adapter.notifyItemRemoved(position);
    }
}
