package com.ofalvai.aircard.presentation.mycards;

import android.Manifest;
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
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.model.MyProfileInfo;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Input form that appears BOTH when editing AND creating a new card.
 * Set ARG_INVOKE_MODE in newInstance() to signal which one is the scenario you have.
 * The activity must implement CardEditFragment.OnFragmentInteractionListener, or an exception is
 * thrown.
 */
@RuntimePermissions
public class CardEditFragment extends DialogFragment {

    public static final String TAG = "CardEditFragment";

    public interface OnFragmentInteractionListener {

        void onCardCreated(Card card);

        void onCardEdited(Card card);

        void onAutofillRequest();
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

    public static CardEditFragment newInstance(@NonNull int invokeMode, OnFragmentInteractionListener listener) {
        CardEditFragment fragment = new CardEditFragment();
        fragment.mListener = listener;

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
                CardColor.DEFAULT // TODO
        );
        card.setTimestampSaved(new DateTime().getMillis());

        return card;
    }

    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    void getAutoFill() {
        if (mListener != null) {
            mListener.onAutofillRequest();
        }
    }

    @OnClick(R.id.card_edit_auto_fill)
    void clickAutoFill() {
        CardEditFragmentPermissionsDispatcher.getAutoFillWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CardEditFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void displayAutoFill(MyProfileInfo info) {
        if (info.name != null && !info.name.isEmpty()) {
            mInputName.setText(info.name);
        }

        if (info.mail != null && !info.mail.isEmpty()) {
            mInputMail.setText(info.mail);
        }

        if (info.phone != null && !info.phone.isEmpty()) {
            mInputPhone.setText(info.phone);
        }

        if (info.address != null && !info.address.isEmpty()) {
            mInputAddress.setText(info.address);
        }
    }
}
