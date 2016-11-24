package com.ofalvai.aircard.presentation.nearbycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearbyCardsFragment extends Fragment implements NearbyCardsContract.View {

    @Nullable
    private NearbyCardsContract.Presenter mPresenter;

    private NearbyCardAdapter mNearbyCardAdapter;

    @BindView(R.id.nearby_cards_list)
    RecyclerView mNearbyCardList;

    @BindView(R.id.nearby_subscribe)
    FloatingActionButton mSubscribeButton;

    public static NearbyCardsFragment newInstance() {
        return new NearbyCardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new NearbyCardsPresenter(getActivity());
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_cards, container, false);

        setHasOptionsMenu(true);

        ButterKnife.bind(NearbyCardsFragment.this, view);

        initCardList();

        if (mPresenter != null) {
            mPresenter.initNearby(getActivity());
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nearby_cards, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_demo_cards:
                if (mPresenter != null) {
                    if (mNearbyCardAdapter.getItemCount() == 0) {
                        mPresenter.getDemoCards();
                    } else {
                        mPresenter.removeDemoCards();
                    }
                }
                break;
        }
        return true;
    }

    private void initCardList() {
        if (mNearbyCardAdapter == null) {
            mNearbyCardAdapter = new NearbyCardAdapter(mPresenter, getActivity());
            mNearbyCardList.setAdapter(mNearbyCardAdapter);
            mNearbyCardList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showCards(List<Card> cards) {
        mNearbyCardAdapter.setCardData(cards);
        mNearbyCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNewCard(Card card) {
        mNearbyCardAdapter.addCard(card);
        mNearbyCardAdapter.notifyItemInserted(mNearbyCardAdapter.getItemCount() - 1);
    }

    @Override
    public void removeCard(Card card) {
        mNearbyCardAdapter.removeCard(card);
        mNearbyCardAdapter.notifyDataSetChanged(); //TODO
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showWarning(String message) {

    }

    @Override
    public void showMessageCardAdded() {
        Snackbar.make(mNearbyCardList, getString(R.string.message_card_saved), Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.nearby_subscribe)
    void clickSubscribe() {
        if (mPresenter != null) {
            mPresenter.startListen();
        }
    }
}
