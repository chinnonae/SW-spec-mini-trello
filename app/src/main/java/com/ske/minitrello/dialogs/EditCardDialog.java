package com.ske.minitrello.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.models.Card;


public class EditCardDialog {

    Card card;
    Activity activity;

    EditText cardTitle;
    EditText cardDesc;

    public EditCardDialog(Card card, Activity activity) {
        this.card = card;
        this.activity = activity;
    }


    public void show() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AddDialogAnimation;
        dialog.setContentView(R.layout.dialog_edit_card);

        cardTitle = (EditText) dialog.findViewById(R.id.card_title_input);
        cardTitle.setText(card.getName());

        cardDesc = (EditText) dialog.findViewById(R.id.card_desc_input);
        cardDesc.setText(card.getDescription());


        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = cardTitle.getText().toString();
                String newDesc = cardDesc.getText().toString();

                CardController.editCardTitle(card, newTitle);
                CardController.editCardDescription(card, newDesc);

                TextView titleLabel = (TextView) activity.findViewById(R.id.card_title);
                titleLabel.setText(newTitle);

                TextView descLabel = (TextView) activity.findViewById(R.id.card_info_description);
                descLabel.setText(newDesc);
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
