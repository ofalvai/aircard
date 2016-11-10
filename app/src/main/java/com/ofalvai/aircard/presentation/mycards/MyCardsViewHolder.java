package com.ofalvai.aircard.presentation.mycards;

import android.view.View;
import android.widget.ImageButton;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCardsViewHolder extends BaseCardViewHolder {

    @BindView(R.id.card_action_edit)
    ImageButton mEditButton;

    @BindView(R.id.card_action_delete)
    ImageButton mDeleteButton;

    public MyCardsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        mEditButton.setVisibility(View.VISIBLE);

        mDeleteButton.setVisibility(View.VISIBLE);
    }
}
