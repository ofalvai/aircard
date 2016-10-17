package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCardsViewHolder extends BaseCardViewHolder {

    @BindView(R.id.card_saved_timestamp)
    TextView mSavedTimestamp;

    @BindView(R.id.card_action_delete)
    ImageButton mDeleteButton;

    public SavedCardsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        mSavedTimestamp.setVisibility(View.VISIBLE);
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindCard(@NonNull Card card, @NonNull Context context) {
        super.bindCard(card, context);

        mSavedTimestamp.setText(stringFromTimestamp(card.getTimestampSaved()));
    }

    private String stringFromTimestamp(long timestamp) {
        DateTime dateTime = new DateTime(timestamp);
        return dateTime.toString();
    }

}
