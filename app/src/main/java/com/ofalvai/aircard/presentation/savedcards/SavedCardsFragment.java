package com.ofalvai.aircard.presentation.savedcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
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
import com.ofalvai.aircard.util.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCardsFragment extends Fragment implements SavedCardsContract.View {

    @Nullable
    private SavedCardsContract.Presenter mPresenter;

    private SavedCardsAdapter mCardAdapter;

    @BindView(R.id.saved_cards_list)
    EmptyRecyclerView mSavedCardsList;

    @BindView(R.id.empty_view)
    TextView mEmptyView;

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

        setHasOptionsMenu(true);

        ButterKnife.bind(SavedCardsFragment.this, view);

        initCardList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_saved_cards, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        initSearchView(searchItem);
    }

    private void initCardList() {
        if (mCardAdapter == null) {
            mCardAdapter = new SavedCardsAdapter(mPresenter, getActivity());
            mSavedCardsList.setAdapter(mCardAdapter);
            mSavedCardsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mEmptyView.setText(R.string.empty_view_saved);
            mSavedCardsList.setEmptyView(mEmptyView);
        }

        if (mPresenter != null) {
            mPresenter.getSavedCards();
        }
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

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void initSearchView(MenuItem menuItem) {
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mPresenter != null) {
                    mPresenter.searchSavedCards(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                showCards(new ArrayList<Card>());
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (mPresenter != null) {
                    mPresenter.getSavedCards();
                }
            }
        });
    }
}
