package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BaseCardAdapter;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;


public class MyCardsAdapter extends BaseCardAdapter {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    private Context mContext;

    public MyCardsAdapter(@Nullable MyCardsContract.Presenter presenter, Context context) {
        super(context);
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public BaseCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_my_card, parent, false);
        return new MyCardsViewHolder(view);
    }

    @Override
    protected void bindListeners(BaseCardViewHolder holder) {
        final MyCardsViewHolder viewHolder = (MyCardsViewHolder) holder;
        final int position = holder.getAdapterPosition();
        final Card card = mCards.get(position);

        viewHolder.mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isCardPublished(card)) {
                    mPresenter.unpublishCard(card);
                    Drawable shareIcon = mContext.getDrawable(R.drawable.ic_share_white_24dp);
                    viewHolder.mShareButton.setImageDrawable(shareIcon);
                } else {
                    mPresenter.publishCard(card);
                    Drawable stopIcon = mContext.getDrawable(R.drawable.ic_cancel_white_24dp);
                    viewHolder.mShareButton.setImageDrawable(stopIcon);
                }
            }
        });

        viewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteMyCard(card);
                mCards.remove(position);
                notifyItemRemoved(position);
            }
        });

        viewHolder.mColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pickCardColor(card);
                notifyItemChanged(position);
            }
        });

        viewHolder.mStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pickCardStyle(card);
                notifyItemChanged(position);
            }
        });
    }
}
