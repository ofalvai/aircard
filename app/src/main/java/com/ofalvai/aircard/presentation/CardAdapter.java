package com.ofalvai.aircard.presentation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.nearbycards.NearbyCardsContract;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Card> mCards;

    private Context mContext;

    //TODO: szétválasztani a nearby és a saved kártyák adapterét
    private NearbyCardsContract.Presenter mPresenter;

    public CardAdapter(@Nullable NearbyCardsContract.Presenter presenter, Context context) {
        this.mCards = new ArrayList<>();
        this.mContext = context;
        this.mPresenter = presenter;
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
        // TODO: ez a pozíció lehet nem lesz pontos, ha lesz törlés, meg ilyesmi
        final Card card = mCards.get(position);
        holder.bindCard(card, mContext);

        if (card.getNote() != null && !card.getNote().isEmpty()) {
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

        holder.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    mPresenter.save(card);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
