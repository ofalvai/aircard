package com.ofalvai.aircard.presentation.mycards;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.model.MyProfileInfo;

import java.util.UUID;

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
public class CardEditFragment extends DialogFragment implements TextWatcher {

    public static final String TAG = "CardEditFragment";

    public interface OnFragmentInteractionListener {

        void onCardCreated(Card card);

        void onCardEdited(Card card);

        void onAutofillRequest();
    }

    public static final String ARG_INVOKE_MODE = "invoke_mode";

    public static final int INVOKE_MODE_CREATE = 100;

    public static final int INVOKE_MODE_EDIT = 101;

    public static final String ARG_EXISTING_CARD = "existing_card";

    private int mInvokeMode;

    @Nullable
    private Card mCurrentCard;

    @Nullable
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.card_edit_name_wrapper)
    TextInputLayout mInputNameWrapper;

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

    public static CardEditFragment newInstance(@NonNull int invokeMode,
                                               OnFragmentInteractionListener listener,
                                               @Nullable Card existingCard) {
        CardEditFragment fragment = new CardEditFragment();
        fragment.mListener = listener;
        fragment.mCurrentCard = existingCard;

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

            // ARG_EXISTING_CARD is handled in onCreateView() because it involves editing views
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_edit, container, false);
        ButterKnife.bind(this, view);

        // Inputs to be validated:
        mInputName.addTextChangedListener(this);

        displayExistingCard(mCurrentCard);

        return view;
    }

    @OnClick(R.id.card_edit_button_cancel)
    void clickCancel() {
        dismiss();
    }

    @OnClick(R.id.card_edit_button_save)
    void clickSave() {
        if (validate()) {
            updateCurrentCardState();

            if (mListener != null) {
                if (mInvokeMode == INVOKE_MODE_CREATE) {
                    mListener.onCardCreated(mCurrentCard);
                } else if (mInvokeMode == INVOKE_MODE_EDIT) {
                    mListener.onCardEdited(mCurrentCard);
                }
            }

            dismiss();
        }
    }

    /**
     * Reads dialog input values and updates the internal mCurrentCard object.
     * If mCurrentCard is null (mostly because INVOKE_MODE_CREATE), creates a new card with a new UUID.
     * This method is used when saving card modifications.
     */
    private void updateCurrentCardState() {
        if (mCurrentCard == null) {
            mCurrentCard = new Card();
            mCurrentCard.setUuid(UUID.randomUUID());
        }
        mCurrentCard.setName(mInputName.getText().toString());
        mCurrentCard.setPhone(mInputPhone.getText().toString());
        mCurrentCard.setMail(mInputMail.getText().toString());
        mCurrentCard.setAddress(mInputAddress.getText().toString());
        // TODO: URL
        mCurrentCard.setNote(mInputNotes.getText().toString());
        mCurrentCard.setCardStyle(CardStyle.NORMAL);
        mCurrentCard.setColor(CardColor.DEFAULT);
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

            if (mCurrentCard != null) {
                mCurrentCard.setName(info.name);
            }
        }

        if (info.mail != null && !info.mail.isEmpty()) {
            mInputMail.setText(info.mail);

            if (mCurrentCard != null) {
                mCurrentCard.setMail(info.mail);
            }
        }

        if (info.phone != null && !info.phone.isEmpty()) {
            mInputPhone.setText(info.phone);

            if (mCurrentCard != null) {
                mCurrentCard.setPhone(info.phone);
            }
        }

        if (info.address != null && !info.address.isEmpty()) {
            mInputAddress.setText(info.address);

            if (mCurrentCard != null) {
                mCurrentCard.setAddress(info.address);
            }
        }
    }

    private void displayExistingCard(@Nullable Card card) {
        if (card == null) return;

        if (card.getName() != null) {
            mInputName.setText(card.getName());
        }

        if (card.getNote() != null) {
            mInputNotes.setText(card.getNote());
        }

        if (card.getMail() != null) {
            mInputMail.setText(card.getMail());
        }

        if (card.getPhone() != null) {
            mInputPhone.setText(card.getPhone());
        }

        if (card.getAddress() != null) {
            mInputAddress.setText(card.getAddress());
        }
    }

    /**
     * Validates text inputs
     * @return true if valid, false otherwise
     */
    private boolean validate() {
        boolean isValid = true;

        if (mInputName.getText().length() == 0) {
            isValid = false;
            mInputNameWrapper.setErrorEnabled(true);
            mInputNameWrapper.setError(getString(R.string.card_edit_invalid_name));
        } else {
            mInputNameWrapper.setErrorEnabled(false);
        }
        return isValid;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // NO OP
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validate();
    }

    @Override
    public void afterTextChanged(Editable s) {
        // NO OP
    }
}
