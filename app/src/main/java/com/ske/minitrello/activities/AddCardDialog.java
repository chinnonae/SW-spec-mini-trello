package com.ske.minitrello.activities;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;

import java.util.Observable;

public class AddCardDialog extends Observable {

    EditText cardTitle;
    EditText cardDesc;

    public void showDialog(Activity activity, final int cardListPosition) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_card_dialog);

        cardTitle = (EditText) dialog.findViewById(R.id.card_title_input);
        cardDesc = (EditText) dialog.findViewById(R.id.card_desc_input);

        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card newCard = new Card(cardTitle.getText().toString(), cardDesc.getText().toString());
                CardList cardList = CardKeeper.getInstance().getLists().get(cardListPosition);
                cardList.addCard(newCard);
                setChanged();
                notifyObservers();
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