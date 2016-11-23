package com.ofalvai.aircard.presentation.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ofalvai.aircard.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void addCard(Card card) {
        mCards.add(card);
    }

    public void removeCard(Card card) {
        // Removing the card by calling remove(card) wouldn't work, because this card object is
        // reconstructed by the Nearby Messages API from a byte array. Therefore it's not
        // the same object as the one in the list, even though their fields are equal.
        // We need to find the card object with the same UUID in the list.
        UUID uuid = card.getUuid();

        Card match = null;
        for (Card localCard : mCards) {
            if (localCard.getUuid().equals(uuid)) {
                match = localCard;
            }
        }

        if (match != null) {
            mCards.remove(match);
        }
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
