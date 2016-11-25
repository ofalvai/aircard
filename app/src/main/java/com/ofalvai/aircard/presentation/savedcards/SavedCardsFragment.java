package com.ofalvai.aircard.presentation.savedcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCardsFragment extends Fragment implements SavedCardsContract.View {

    @Nullable
    private SavedCardsContract.Presenter mPresenter;

    private SavedCardsAdapter mCardAdapter;

    @BindView(R.id.saved_cards_list)
    RecyclerView mSavedCardsList;

    public static SavedCardsFragment newInstance() {
        return new SavedCardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SavedCardsPresenter(getActivity());
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_cards, container, false);

        ButterKnife.bind(SavedCardsFragment.this, view);

        initCardList();

        return view;
    }

    private void initCardList() {
        if (mCardAdapter == null) {
            mCardAdapter = new SavedCardsAdapter(mPresenter, getActivity());
            mSavedCardsList.setAdapter(mCardAdapter);
            mSavedCardsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        mPresenter.getSavedCards();
    }

    @Override
    public void showCards(List<Card> cards) {
        mCardAdapter.setCardData(cards);
        mCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshSavedCards() {
        if (mPresenter != null) {
            mPresenter.getSavedCards();
        }
    }
}
