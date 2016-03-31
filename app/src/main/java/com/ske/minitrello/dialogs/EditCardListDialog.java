package com.ske.minitrello.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.controllers.CardController;
import com.ske.minitrello.models.CardList;

/**
 * Created by Peter on 3/31/2016 AD.
 */
public class EditCardListDialog {

    CardList cardList;
    Activity activity;

    EditText cardListTitle;

    public EditCardListDialog(CardList cardList, Activity activity) {
        this.cardList = cardList;
        this.activity = activity;
    }

    public void show() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AddDialogAnimation;
        dialog.setContentView(R.layout.dialog_edit_cardlist);

        cardListTitle = (EditText) dialog.findViewById(R.id.edit_cardlist_title);
        cardListTitle.setText(cardList.getName());

        TextView confirmButton = (TextView) dialog.findViewById(R.id.dialog_confirm_btn);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.dialog_cancle_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = cardListTitle.getText().toString();
                CardController.renameCardList(cardList, newTitle);
//                TextView listTitleLabel = (TextView) activity.findViewById(R.id.cardlist_title);
//                listTitleLabel.setText(newTitle);
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
