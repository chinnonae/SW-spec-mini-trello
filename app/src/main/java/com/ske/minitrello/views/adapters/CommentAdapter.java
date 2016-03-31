package com.ske.minitrello.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> comments;
    private CommentItemClickListener listener;

    public CommentAdapter(List<Comment> comments, CommentItemClickListener listener) {
        this.comments = comments;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView content;
        TextView createdTime;

        public ViewHolder(View v) {
            super(v);
            content = (TextView)v.findViewById(R.id.comment_content);
            createdTime = (TextView)v.findViewById(R.id.comment_creation_time);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, vh.getAdapterPosition());
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.content.setText(comment.getContent());
        holder.createdTime.setText(comment.getRelativeTime());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}