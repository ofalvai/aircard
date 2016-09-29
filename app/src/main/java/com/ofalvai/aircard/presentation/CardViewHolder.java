package com.ofalvai.aircard.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewHolder extends RecyclerView.ViewHolder {

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

    @BindView(R.id.card_saved_timestamp)
    TextView mSavedTimestamp;

    public CardViewHolder(View itemView) {
        super(itemView);

        mCardView = (CardView) itemView;

        ButterKnife.bind(this, itemView);
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

        // Card is displayed in the saved cards list (not in the nearby list)
        if (card.getTimestampSaved() != 0) {
            mSaveButton.setVisibility(View.GONE);
            mSavedTimestamp.setText(stringFromTimestamp(card.getTimestampSaved()));
        } else {
            mSavedTimestamp.setVisibility(View.GONE);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickSave();
                }
            });
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

    private void clickSave() {
        Log.d("CardViewHolder", "onClick: ");
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

    private String stringFromTimestamp(long timestamp) {
        DateTime dateTime = new DateTime(timestamp);
        return dateTime.toString();
    }

}
