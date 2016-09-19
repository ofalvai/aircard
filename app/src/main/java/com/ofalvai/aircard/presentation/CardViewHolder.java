package com.ofalvai.aircard.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CustomField;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.list_item_card_content)
    TextView mCardContent;

    public CardViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


    public void bindCard(@NonNull Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(card.getName());
        stringBuilder.append("\n");
        for (CustomField field : card.getCustomFields()) {
            stringBuilder.append(field.getLabel());
            stringBuilder.append(": ");
            stringBuilder.append(field.getValue());
            stringBuilder.append("\n");
        }

        mCardContent.setText(stringBuilder.toString());
    }

    @Override
    public void onClick(View view) {

    }
}
