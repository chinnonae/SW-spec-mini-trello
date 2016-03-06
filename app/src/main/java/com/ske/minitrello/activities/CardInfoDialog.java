package com.ske.minitrello.activities;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.models.Card;

import java.util.Observable;


/**
 * Created by Peter on 3/6/2016 AD.
 */
public class CardInfoDialog extends Observable {

    TextView cardTitle;
    TextView cardDescription;
    ImageView editTitleBtn;
    ImageView editDescBtn;
    ImageView addCommentBtn;
    ImageView closeBtn;
    RecyclerView recyclerView;

    Card card;

    public CardInfoDialog(Card card) {
        this.card = card;
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_card_info);

        cardTitle = (TextView) dialog.findViewById(R.id.card_dialog_title);
        cardDescription = (TextView) dialog.findViewById(R.id.card_dialog_description);

        closeBtn = (ImageView) dialog.findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cardTitle.setText(card.getName());
        cardDescription.setText(card.getDescription());

        dialog.show();

    }
}
