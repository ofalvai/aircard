package com.ofalvai.aircard.presentation.nearbycards;

import android.view.View;
import android.widget.Button;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyCardsViewHolder extends BaseCardViewHolder {

    @BindView(R.id.card_action_save)
    Button mSaveButton;

    public NearbyCardsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        mSaveButton.setVisibility(View.VISIBLE);
    }
}
