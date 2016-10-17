package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BaseCardAdapter;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;

public class SavedCardsAdapter extends BaseCardAdapter {

    @Nullable
    private final SavedCardsContract.Presenter mPresenter;

    public SavedCardsAdapter(@Nullable SavedCardsContract.Presenter presenter, Context context) {
        super(context);
        mPresenter = presenter;
    }

    @Override
    public SavedCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);
        return new SavedCardsViewHolder(view);
    }

    @Override
    protected void bindListeners(BaseCardViewHolder holder, int position) {
        SavedCardsViewHolder viewHolder = (SavedCardsViewHolder) holder;
        final Card card = mCards.get(position);

        viewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    //TODO
                }
            }
        });


    }
}
