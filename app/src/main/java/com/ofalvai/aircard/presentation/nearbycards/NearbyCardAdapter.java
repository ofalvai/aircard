package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BaseCardAdapter;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

public class NearbyCardAdapter extends BaseCardAdapter {

    @Nullable
    private NearbyCardsContract.Presenter mPresenter;

    public NearbyCardAdapter(@Nullable NearbyCardsContract.Presenter presenter, Context context) {
        super(context);
        this.mPresenter = presenter;
    }

    @Override
    public NearbyCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);
        return new NearbyCardsViewHolder(view);
    }

    @Override
    protected void bindListeners(BaseCardViewHolder holder) {
        final NearbyCardsViewHolder viewHolder = (NearbyCardsViewHolder) holder;

        viewHolder.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card card = mCards.get(viewHolder.getAdapterPosition());
                if (mPresenter != null) {
                    mPresenter.save(card);
                }
            }
        });
    }
}
