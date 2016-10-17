package com.ofalvai.aircard.presentation.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;

/**
 * Base class for a ViewHolder containing the cards' common views.
 * Any other view can be defined in a subclass, but make sure to bind them in the constructor!
 */
public class BaseCardViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;

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

    /**
     * Make sure to call super() and then Butterknife.bind() in the subclass constructor
     */
    public BaseCardViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView;
    }

    public void bindCard(@NonNull final Card card, @NonNull final Context context) {
        // Title
        mCardTitle.setText(card.getName());
        setTypeface(mCardTitle, card.getCardStyle());

        // Mail
        if (card.getMail() != null && !card.getMail().isEmpty()) {
            mCardMail.setText(card.getMail());
            setTypeface(mCardMail, card.getCardStyle());
            mCardMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startEmailIntent(card.getMail(), context);
                }
            });
        } else {
            mCardMail.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_mail).setVisibility(View.GONE);
        }

        // Phone number
        if (card.getPhone() != null && !card.getPhone().isEmpty()) {
            mCardTel.setText(card.getPhone());
            setTypeface(mCardTel, card.getCardStyle());
            mCardTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTelIntent(card.getPhone(), context);
                }
            });
        } else {
            mCardTel.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_tel).setVisibility(View.GONE);
        }

        // Location
        if (card.getAddress() != null && !card.getAddress().isEmpty()) {
            mCardLocation.setText(card.getAddress());
            setTypeface(mCardLocation, card.getCardStyle());
            mCardLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickLocation(card.getAddress(), context);
                }
            });
        } else {
            mCardLocation.setVisibility(View.GONE);
            mCardView.findViewById(R.id.card_icon_location).setVisibility(View.GONE);
        }

        // Note
        if (card.getNote() == null || card.getNote().isEmpty()) {
            mCardNote.setVisibility(View.GONE);
        } else {
            mCardNote.setText(card.getNote());
            setTypeface(mCardNote, card.getCardStyle());
        }

        // Background color
        if (card.getColor() != null && !card.getColor().isEmpty()) {
            mCardView.setCardBackgroundColor(Color.parseColor("#" + card.getColor()));
        }
    }

    private void setTypeface(TextView textView, CardStyle cardStyle) {
        if (textView != null && cardStyle != null) {
            switch (cardStyle) {
                case NORMAL:
                    break;
                case MONOSPACE:
                    textView.setTypeface(Typeface.MONOSPACE);
                    break;
                case SERIF:
                    textView.setTypeface(Typeface.SERIF);
                    break;
            }
        }
    }

    private void startEmailIntent(String address, Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getString(R.string.error_intent_email), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void startTelIntent(String number, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getString(R.string.error_intent_tel), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void clickLocation(String location, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String locationEncoded = URLEncoder.encode(location, "utf-8");
            intent.setData(Uri.parse("geo:0,0?q=" + locationEncoded));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, context.getString(R.string.error_intent_location),
                        Toast.LENGTH_LONG).show();
            }

        } catch (UnsupportedEncodingException ex) {
            Toast.makeText(context, context.getString(R.string.error_intent_location_invalid),
                    Toast.LENGTH_LONG).show();
        }
    }
}
