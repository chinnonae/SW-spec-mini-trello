package com.ske.minitrello.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.Comment;
import com.ske.minitrello.views.adapters.CommentAdapter;

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
        commentAdapter = new CommentAdapter(comments);
        comments.add(new Comment("Hello"));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recyclerview);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}
