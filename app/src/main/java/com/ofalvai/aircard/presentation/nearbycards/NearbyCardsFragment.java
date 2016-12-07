package com.ofalvai.aircard.presentation.nearbycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.github.jorgecastilloprz.FABProgressCircle;
import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.util.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearbyCardsFragment extends Fragment implements NearbyCardsContract.View {

    @Nullable
    private NearbyCardsContract.Presenter mPresenter;

    private NearbyCardAdapter mNearbyCardAdapter;

    @BindView(R.id.nearby_cards_list)
    EmptyRecyclerView mNearbyCardList;

    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @BindView(R.id.nearby_subscribe)
    FloatingActionButton mSubscribeButton;

    @BindView(R.id.nearby_subscribe_progress_circle)
    FABProgressCircle mSubscribeButtonCircle;

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
                toggleDemoCards();
                break;
        }
        return true;
    }

    private void initCardList() {
        if (mNearbyCardAdapter == null) {
            mNearbyCardAdapter = new NearbyCardAdapter(mPresenter, getActivity());
            mNearbyCardList.setAdapter(mNearbyCardAdapter);
            mNearbyCardList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mEmptyView.setText(R.string.empty_view_nearby_disabled);
            mNearbyCardList.setEmptyView(mEmptyView);
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
        // Adding new items to the top of the list
        mNearbyCardAdapter.addCard(0, card);
        mNearbyCardAdapter.notifyItemInserted(0);
        mNearbyCardList.smoothScrollToPosition(0);
    }

    @Override
    public void removeCard(Card card) {
        mNearbyCardAdapter.removeCard(card);
        mNearbyCardAdapter.notifyDataSetChanged(); //TODO
    }

    @Override
    public void showMessageCardAdded() {
        Snackbar.make(mNearbyCardList, getString(R.string.message_card_saved), Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.nearby_subscribe)
    void clickSubscribe() {
        if (mPresenter != null) {
            if (mPresenter.isSubscribed()) {
                mEmptyView.setText(R.string.empty_view_nearby_disabled);
                mPresenter.unsubscribe();
                mSubscribeButtonCircle.hide();
                // Note that we don't clear discovered cards, so the user has time to save them
                // even after turning Nearby off.
            } else {
                // Clearing previously discovered cards (and demo cards)
                showCards(new ArrayList<Card>());
                mPresenter.subscribe();
                mSubscribeButtonCircle.show();
            }
        }
    }

    @Override
    public void setStateSubscribing() {
        mEmptyView.setText(R.string.empty_view_nearby_searching);
    }

    @Override
    public void setStateNotSubscribing() {
        mEmptyView.setText(R.string.empty_view_nearby_disabled);
        mSubscribeButtonCircle.hide();
    }

    @Override
    public void showSubscribeError(String message) {
        Snackbar.make(mNearbyCardList, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void toggleDemoCards() {
        if (mPresenter != null) {
            if (mPresenter.isSubscribed()) {
                Toast.makeText(getActivity(), getString(R.string.nearby_demo_cards_subscribed),
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (mNearbyCardAdapter.getItemCount() == 0) {
                mPresenter.getDemoCards();
            } else {
                mPresenter.removeDemoCards();
            }
        }
    }
}
