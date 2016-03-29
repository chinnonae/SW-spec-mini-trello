package com.ske.minitrello.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.controllers.CardController;

public class AddCardDialog {

    EditText cardTitle;
    EditText cardDesc;

    public void showDialog(Activity activity, final int cardListPosition) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AddDialogAnimation;
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_card);

        cardTitle = (EditText) dialog.findViewById(R.id.card_title_input);
        cardDesc = (EditText) dialog.findViewById(R.id.card_desc_input);

        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Card newCard = new Card(cardTitle.getText().toString(), cardDesc.getText().toString());
                CardList cardList = CardKeeper.getInstance().getLists().get(cardListPosition);

                CardController.addCard(newCard, cardList);

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