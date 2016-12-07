package com.ofalvai.aircard.presentation.mycards;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;
import com.ofalvai.aircard.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCardsViewHolder extends BaseCardViewHolder {

    @BindView(R.id.card_action_publish)
    FloatingActionButton mPublishButton;

    @BindView(R.id.card_action_publish_progress_circle)
    FABProgressCircle mPublishButtonCircle;

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

    public void setCardStatePublished() {

        Drawable stopIcon = ContextCompat.getDrawable(itemView.getContext(),
                R.drawable.ic_cancel_white_24dp);
        mPublishButton.setImageDrawable(stopIcon);
    }

    public void setCardStateUnpublished() {
        Drawable publishIcon = ContextCompat.getDrawable(itemView.getContext(),
                R.drawable.ic_speaker_phone_white_24dp);
        mPublishButton.setImageDrawable(publishIcon);
        mPublishButtonCircle.hide();

        enableButtons();

        final CardView cardItemView = (CardView) itemView.findViewById(R.id.card);
        cardItemView.setCardElevation(Utils.convertDpToPx(itemView.getContext(), 2.0f));
    }

    public void setCardStatePublishing() {
        Drawable stopIcon = ContextCompat.getDrawable(itemView.getContext(),
                R.drawable.ic_cancel_white_24dp);
        mPublishButton.setImageDrawable(stopIcon);
        mPublishButtonCircle.show();

        disableButtons();

        final CardView cardItemView = (CardView) itemView.findViewById(R.id.card);
        cardItemView.setCardElevation(Utils.convertDpToPx(itemView.getContext(), 8.0f));
    }

    private void disableButtons() {
        mDeleteButton.setAlpha(0.5f);
        mDeleteButton.setEnabled(false);

        mEditButton.setAlpha(0.5f);
        mEditButton.setEnabled(false);

        mColorButton.setAlpha(0.5f);
        mColorButton.setEnabled(false);

        mStyleButton.setAlpha(0.5f);
        mStyleButton.setEnabled(false);
    }

    private void enableButtons() {
        mDeleteButton.setAlpha(1.0f);
        mDeleteButton.setEnabled(true);

        mEditButton.setAlpha(1.0f);
        mEditButton.setEnabled(true);

        mColorButton.setAlpha(1.0f);
        mColorButton.setEnabled(true);

        mStyleButton.setAlpha(1.0f);
        mStyleButton.setEnabled(true);
    }
}
