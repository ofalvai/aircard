package com.ofalvai.aircard.presentation.mycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This dialog appears when the user click on the style picker icon of a card in the list of My Cards
 */
public class CardStyleFragment extends DialogFragment {
    public static final String TAG = "CardStyleFragment";

    public static final String ARG_CARD_UUID = "card_uuid";

    private UUID mCardUuid;

    public interface OnFragmentInteractionListener {

        void cardStyleChanged(UUID cardUuid, CardStyle newStyle);
    }

    @Nullable
    private OnFragmentInteractionListener mListener;

    public CardStyleFragment() {
        // Required empty public constructor
    }

    public static CardStyleFragment newInstance(Card card, OnFragmentInteractionListener listener) {
        CardStyleFragment fragment = new CardStyleFragment();
        fragment.mListener = listener;

        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD_UUID, card.getUuid());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCardUuid = (UUID) getArguments().getSerializable(ARG_CARD_UUID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_style, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.card_style_default)
    void clickDefault() {
        if (mListener != null) {
            mListener.cardStyleChanged(mCardUuid, CardStyle.NORMAL);
        }
        dismiss();
    }

    @OnClick(R.id.card_style_monospace)
    void clickYellow() {
        if (mListener != null) {
            mListener.cardStyleChanged(mCardUuid, CardStyle.MONOSPACE);
        }
        dismiss();
    }

    @OnClick(R.id.card_style_serif)
    void clickOrange() {
        if (mListener != null) {
            mListener.cardStyleChanged(mCardUuid, CardStyle.SERIF);
        }
        dismiss();
    }
}