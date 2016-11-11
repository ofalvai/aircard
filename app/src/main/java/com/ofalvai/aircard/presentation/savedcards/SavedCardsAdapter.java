package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    protected void bindListeners(BaseCardViewHolder holder) {
        final SavedCardsViewHolder viewHolder = (SavedCardsViewHolder) holder;
        viewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deleteCard(viewHolder);
            }
        });
    }

    private void deleteCard(RecyclerView.ViewHolder viewHolder) {
        if (mPresenter != null) {
            try {
                int position = viewHolder.getAdapterPosition();
                Card card = mCards.get(position);
                mPresenter.deleteSavedCard(card);
                mCards.remove(position);
                notifyItemRemoved(position);
            } catch (Exception ex) {
                Toast.makeText(mContext, R.string.error_delete_saved_card, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
