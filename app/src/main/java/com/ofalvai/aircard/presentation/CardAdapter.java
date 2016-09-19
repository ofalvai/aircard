package com.ofalvai.aircard.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Card> mCards;

    private Context mContext;

    public CardAdapter(List<Card> cardList, Context context) {
        this.mCards = cardList;
        this.mContext = context;
    }

    public void setCardData(List<Card> cards) {
        mCards = cards;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card card = mCards.get(position);
        holder.bindCard(card);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
