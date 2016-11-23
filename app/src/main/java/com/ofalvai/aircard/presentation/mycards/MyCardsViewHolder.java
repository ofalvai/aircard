package com.ofalvai.aircard.presentation.mycards;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCardsViewHolder extends BaseCardViewHolder {

    @BindView(R.id.card_action_share)
    FloatingActionButton mShareButton;

    @BindView(R.id.card_action_edit)
    ImageButton mEditButton;

    @BindView(R.id.card_action_delete)
    ImageButton mDeleteButton;

    @BindView(R.id.card_action_change_color)
    ImageButton mColorButton;

    @BindView(R.id.card_action_change_style)
    ImageButton mStyleButton;

    public MyCardsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
