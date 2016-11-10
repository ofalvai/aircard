package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This dialog appears when the user click on the color picker icon of a card in the list of My Cards
 */
public class CardColorFragment extends DialogFragment {
    public static final String TAG = "CardColorFragment";

    public static final String ARG_CARD_UUID = "card_uuid";

    private String mCardUuid;

    public interface OnFragmentInteractionListener {


    }

    @Nullable
    private OnFragmentInteractionListener mListener;

    public CardColorFragment() {
        // Required empty public constructor
    }

    public static CardColorFragment newInstance(Card card) {
        CardColorFragment fragment = new CardColorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CARD_UUID, card.getUuid().toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCardUuid = getArguments().getString(ARG_CARD_UUID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_color, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CardEditFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.card_color_preview_row_white)
    void clickDefault() {

    }

    @OnClick(R.id.card_color_preview_row_yellow)
    void clickYellow() {

    }

    @OnClick(R.id.card_color_preview_row_orange)
    void clickOrange() {

    }
}
