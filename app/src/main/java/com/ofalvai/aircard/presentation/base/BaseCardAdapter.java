package com.ofalvai.aircard.presentation.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ofalvai.aircard.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for a card list item's adapter.
 * Subclasses can have constructor parameters for different Presenters and can override
 * bindListeners().
 * onCreateViewHolder must be overridden to return the subclass' type.
 */
public abstract class BaseCardAdapter extends RecyclerView.Adapter<BaseCardViewHolder> {

    protected List<Card> mCards;

    protected Context mContext;

    public BaseCardAdapter(Context context) {
        this.mContext = context;
        this.mCards = new ArrayList<>();
    }

    /**
     * Can be overridden to attach event listeners to views. This gets called during binding the
     * ViewHolder
     */
    protected abstract void bindListeners(BaseCardViewHolder holder);

    public void setCardData(List<Card> cards) {
        mCards = cards;
    }

    @Override
    public void onBindViewHolder(BaseCardViewHolder holder, int position) {
        final Card card = mCards.get(position);
        holder.bindCard(card, mContext);

        bindListeners(holder);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
