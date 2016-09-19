package com.ofalvai.aircard.presentation;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CardView mCardView;

    @BindView(R.id.card_title)
    TextView mCardTitle;

    @BindView(R.id.card_mail)
    TextView mCardMail;

    @BindView(R.id.card_tel)
    TextView mCardTel;

    @BindView(R.id.card_location)
    TextView mCardLocation;

    @BindView(R.id.card_note)
    TextView mCardNote;

    @BindView(R.id.card_action_save)
    Button mSaveButton;

    public CardViewHolder(View itemView) {
        super(itemView);

        mCardView = (CardView) itemView;

        ButterKnife.bind(this, itemView);
    }

    public void bindCard(@NonNull Card card) {
        // Title
        mCardTitle.setText(card.getName());
        setTypeface(mCardTitle, card.getTypeface());

        // Mail
        if (card.getMail() != null && !card.getMail().isEmpty()) {
            mCardMail.setText(card.getMail());
            setTypeface(mCardMail, card.getTypeface());
        } else {
            mCardMail.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_mail).setVisibility(View.GONE);
        }

        // Phone number
        if (card.getTel() != null && !card.getTel().isEmpty()) {
            mCardTel.setText(card.getTel());
            setTypeface(mCardTel, card.getTypeface());
        } else {
            mCardTel.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_tel).setVisibility(View.GONE);
        }

        // Location
        if (card.getAddress() != null && !card.getAddress().isEmpty()) {
            mCardLocation.setText(card.getAddress());
            setTypeface(mCardLocation, card.getTypeface());
        } else {
            mCardLocation.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_location).setVisibility(View.GONE);
        }

        // Note
        if (card.getNote() == null || card.getNote().isEmpty()) {
            mCardNote.setVisibility(View.GONE);
        } else {
            mCardNote.setText(card.getNote());
            setTypeface(mCardNote, card.getTypeface());
        }

        // Background color
        if (card.getColor() != null && !card.getColor().isEmpty()) {
            mCardView.setCardBackgroundColor(Color.parseColor("#" + card.getColor()));
        }

        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("CardViewHolder", "onClick: ");
    }

    private void setTypeface(TextView textView, int typeface) {
        if (textView != null && typeface > 0) {
            switch (typeface) {
                case Card.TYPEFACE_NORMAL:
                    break;
                case Card.TYPEFACE_MONOSPACE:
                    textView.setTypeface(Typeface.MONOSPACE);
                    break;
                case Card.TYPEFACE_SERIF:
                    textView.setTypeface(Typeface.SERIF);
                    break;
            }
        }
    }

}
