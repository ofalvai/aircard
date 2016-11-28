package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BaseCardAdapter;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

import java.util.UUID;


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

        viewHolder.mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                final Card card = mCards.get(position);

                if (mPresenter.isCardPublished(card)) {
                    mPresenter.unpublishCard(card);
                    viewHolder.setCardStateUnpublished();
                } else {
                    mPresenter.publishCard(card);
                    viewHolder.setCardStatePublishing();
                }
            }
        });

        viewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                final Card card = mCards.get(position);

                mPresenter.deleteMyCard(card);
                mCards.remove(position);
                notifyItemRemoved(position);
            }
        });

        viewHolder.mColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                final Card card = mCards.get(position);

                mPresenter.pickCardColor(card);
                notifyItemChanged(position);
            }
        });

        viewHolder.mStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                final Card card = mCards.get(position);

                mPresenter.pickCardStyle(card);
                notifyItemChanged(position);
            }
        });
    }

    public int getCardPosition(UUID uuid) {
        int position = -1;

        for (Card card : mCards) {
            if (card.getUuid().equals(uuid)) {
                position = mCards.indexOf(card);
                break;
            }
        }

        return position;
    }


}
