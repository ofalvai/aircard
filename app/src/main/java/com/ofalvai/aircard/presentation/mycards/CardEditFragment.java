package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Input form that appears BOTH when editing AND creating a new card.
 * Set ARG_INVOKE_MODE in newInstance() to signal which one is the scenario you have.
 * The activity must implement CardEditFragment.OnFragmentInteractionListener, or an exception is
 * thrown.
 */
public class CardEditFragment extends DialogFragment {

    public static final String TAG = "CardEditFragment";

    public interface OnFragmentInteractionListener {

        void onCardCreated(Card card);

        void onCardEdited(Card card);

    }

    public static final String ARG_INVOKE_MODE = "invoke_mode";

    public static final int INVOKE_MODE_CREATE = 100;

    public static final int INVOKE_MODE_EDIT = 101;

    private int mInvokeMode;

    @Nullable
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.card_edit_name)
    EditText mInputName;

    @BindView(R.id.card_edit_notes)
    EditText mInputNotes;

    @BindView(R.id.card_edit_mail)
    EditText mInputMail;

    @BindView(R.id.card_edit_phone)
    EditText mInputPhone;

    @BindView(R.id.card_edit_address)
    EditText mInputAddress;

    public CardEditFragment() {
        // Required empty public constructor
    }

    public static CardEditFragment newInstance(@NonNull int invokeMode) {
        CardEditFragment fragment = new CardEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INVOKE_MODE, invokeMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInvokeMode = getArguments().getInt(ARG_INVOKE_MODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_edit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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

    @OnClick(R.id.card_edit_button_cancel)
    void clickCancel() {
        dismiss();
    }

    @OnClick(R.id.card_edit_button_save)
    void clickSave() {
        if (mListener != null) {
            if (mInvokeMode == INVOKE_MODE_CREATE) {
                mListener.onCardCreated(getCardFromInputData());
            } else if (mInvokeMode == INVOKE_MODE_EDIT) {
                mListener.onCardEdited(getCardFromInputData());
            }
        }

        dismiss();
    }

    private Card getCardFromInputData() {
        Card card = new Card(
                null, // UUID is unknown at this time
                mInputName.getText().toString(),
                mInputPhone.getText().toString(),
                mInputMail.getText().toString(),
                mInputAddress.getText().toString(),
                null, //TODO
                mInputNotes.getText().toString(),
                CardStyle.NORMAL, //TODO
                "ffffff" // TODO
        );
        card.setTimestampSaved(new DateTime().getMillis());

        return card;
    }
}
