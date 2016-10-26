package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.base.BaseCardAdapter;
import com.ofalvai.aircard.presentation.base.BaseCardViewHolder;


public class MyCardsAdapter extends BaseCardAdapter {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    public MyCardsAdapter(@Nullable MyCardsContract.Presenter presenter, Context context) {
        super(context);
        this.mPresenter = presenter;
    }

    @Override
    public BaseCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_my_card, parent, false);
        return new MyCardsViewHolder(view);
    }

    @Override
    protected void bindListeners(BaseCardViewHolder holder, int position) {

    }
}
