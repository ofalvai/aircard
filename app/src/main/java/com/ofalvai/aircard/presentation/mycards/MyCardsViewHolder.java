package com.ofalvai.aircard.presentation.mycards;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

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
    }

    public void setCardStatePublishing() {
        Drawable stopIcon = ContextCompat.getDrawable(itemView.getContext(),
                R.drawable.ic_cancel_white_24dp);
        mPublishButton.setImageDrawable(stopIcon);
        mPublishButtonCircle.show();
    }
}
