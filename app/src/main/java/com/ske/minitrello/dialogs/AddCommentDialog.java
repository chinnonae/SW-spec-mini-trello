package com.ske.minitrello.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.controllers.CommentController;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.models.Comment;


public class AddCommentDialog {

    Card card;
    Activity activity;

    EditText commentMsg;

    public AddCommentDialog(Card card, Activity activity) {
        this.card = card;
        this.activity= activity;
    }

    public void show() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AddDialogAnimation;
        dialog.setContentView(R.layout.dialog_add_comment);

        commentMsg = (EditText) dialog.findViewById(R.id.edittext_comment);

        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment(commentMsg.getText().toString());
                CommentController.addComment(comment, card);
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
