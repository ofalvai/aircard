package com.ofalvai.aircard.presentation.mycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyCardsFragment extends Fragment implements
        MyCardsContract.View,
        CardEditFragment.OnFragmentInteractionListener,
        CardColorFragment.OnFragmentInteractionListener,
        CardStyleFragment.OnFragmentInteractionListener {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    private MyCardsAdapter mMyCardsAdapter;

    @BindView(R.id.my_cards_list)
    RecyclerView mMyCardsList;

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
                showCreateFragment();
                break;
        }
        return true;
    }

    private void showCreateFragment() {
        CardEditFragment fragment = CardEditFragment.newInstance(
                CardEditFragment.INVOKE_MODE_CREATE,
                MyCardsFragment.this
        );
        fragment.show(getActivity().getSupportFragmentManager(), CardEditFragment.TAG);
    }

    private void initCardList() {
        if (mMyCardsAdapter == null) {
            mMyCardsAdapter = new MyCardsAdapter(mPresenter, getActivity());
            mMyCardsList.setAdapter(mMyCardsAdapter);
            mMyCardsList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
}
