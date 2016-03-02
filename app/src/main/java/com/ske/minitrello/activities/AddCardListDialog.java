package com.ske.minitrello.activities;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;


import java.util.Observable;


public class AddCardListDialog extends Observable {

    EditText listTitle;

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_card_list_dialog);

        listTitle = (EditText) dialog.findViewById(R.id.cardlist_title);

        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CardList newList = new CardList(listTitle.getText().toString());
                CardKeeper.getInstance().addCardList(newList);

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
