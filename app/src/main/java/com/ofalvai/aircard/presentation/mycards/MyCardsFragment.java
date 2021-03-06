package com.ofalvai.aircard.presentation.mycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.model.MyProfileInfo;
import com.ofalvai.aircard.util.EmptyRecyclerView;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyCardsFragment extends Fragment implements
        MyCardsContract.View,
        CardEditFragment.OnFragmentInteractionListener,
        CardColorFragment.OnFragmentInteractionListener,
        CardStyleFragment.OnFragmentInteractionListener {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    private MyCardsAdapter mMyCardsAdapter;

    @BindView(R.id.my_cards_list)
    EmptyRecyclerView mMyCardsList;

    @BindView(R.id.empty_view)
    TextView mEmptyView;

    public static MyCardsFragment newInstance() {
        return new MyCardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MyCardsPresenter(getActivity());
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cards, container, false);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        initCardList();

        mPresenter.initNearby(getActivity());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_my_cards, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_card:
                showCreateDialog();
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    private void showCreateDialog() {
        CardEditFragment fragment = CardEditFragment.newInstance(
                CardEditFragment.INVOKE_MODE_CREATE,
                MyCardsFragment.this,
                null
        );
        fragment.show(getActivity().getSupportFragmentManager(), CardEditFragment.TAG);
    }

    @Override
    public void showEditDialog(Card card) {
        CardEditFragment fragment = CardEditFragment.newInstance(
                CardEditFragment.INVOKE_MODE_EDIT,
                MyCardsFragment.this,
                card
        );
        fragment.show(getActivity().getSupportFragmentManager(), CardEditFragment.TAG);
    }

    private void initCardList() {
        if (mMyCardsAdapter == null) {
            mMyCardsAdapter = new MyCardsAdapter(mPresenter, getActivity());
            mMyCardsList.setAdapter(mMyCardsAdapter);
            mMyCardsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mEmptyView.setText(R.string.empty_view_my_cards);
            mMyCardsList.setEmptyView(mEmptyView);
        }

        if (mPresenter != null) {
            mPresenter.getMyCards();
        }
    }

    @Override
    public void showCards(List<Card> cards) {
        mMyCardsAdapter.setCardData(cards);
        mMyCardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardCreated(Card card) {
        if (mPresenter != null) {
            mPresenter.newCard(card);
        }
    }

    @Override
    public void onCardEdited(Card card) {
        if (mPresenter != null) {
            mPresenter.editCard(card);
            mPresenter.getMyCards();
        }
    }

    @Override
    public void showColorPicker(Card card) {
        CardColorFragment fragment = CardColorFragment.newInstance(card, MyCardsFragment.this);
        fragment.show(getActivity().getSupportFragmentManager(), CardColorFragment.TAG);
    }

    @Override
    public void cardColorChanged(UUID cardUuid, CardColor newColor) {
        if (mPresenter != null) {
            mPresenter.updateCardColor(cardUuid, newColor);
            mPresenter.getMyCards();
        }
    }

    @Override
    public void showStylePicker(Card card) {
        CardStyleFragment fragment = CardStyleFragment.newInstance(card, MyCardsFragment.this);
        fragment.show(getActivity().getSupportFragmentManager(), CardStyleFragment.TAG);
    }

    @Override
    public void cardStyleChanged(UUID cardUuid, CardStyle newStyle) {
        if (mPresenter != null) {
            mPresenter.updateCardStyle(cardUuid, newStyle);
            mPresenter.getMyCards();
        }
    }

    @Override
    public void onAutofillRequest() {
        if (mPresenter != null) {
            mPresenter.getMyProfileInfo();
        }
    }

    @Override
    public void showMyProfileInfo(MyProfileInfo info) {
        CardEditFragment fragment = (CardEditFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(CardEditFragment.TAG);
        fragment.displayAutoFill(info);
    }

    @Override
    public void showMyProfileInfoError() {
        String message = getActivity().getString(R.string.auto_fill_error);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCardStatePublished(UUID uuid) {
        int position = mMyCardsAdapter.getCardPosition(uuid);

        if (position >= 0) {
            MyCardsViewHolder holder = (MyCardsViewHolder)
                    mMyCardsList.findViewHolderForAdapterPosition(position);
            holder.setCardStatePublished();
        }
    }

    @Override
    public void setCardStateUnpublished(UUID uuid) {
        int position = mMyCardsAdapter.getCardPosition(uuid);

        if (position >= 0) {
            MyCardsViewHolder holder = (MyCardsViewHolder)
                    mMyCardsList.findViewHolderForAdapterPosition(position);
            holder.setCardStateUnpublished();
        }
    }

    @Override
    public void showPublishError(String message) {
        Snackbar.make(mMyCardsList, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.empty_view)
    void clickEmptyView() {
        showCreateDialog();
    }
}
