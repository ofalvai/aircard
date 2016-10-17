package com.ofalvai.aircard.presentation.savedcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.CardAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCardsActivity extends AppCompatActivity implements SavedCardsContract.View {

    @Nullable
    private SavedCardsContract.Presenter mPresenter;

    private CardAdapter mCardAdapter;

    @BindView(R.id.saved_cards_list)
    RecyclerView mSavedCardsList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cards);

        ButterKnife.bind(this);

        mPresenter = new SavedCardsPresenter(SavedCardsActivity.this);
        mPresenter.attachView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCardList();
    }

    private void initCardList() {
        if (mCardAdapter == null) {
            mCardAdapter = new CardAdapter(null, SavedCardsActivity.this); //TODO
            mSavedCardsList.setAdapter(mCardAdapter);
            mSavedCardsList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }

        mPresenter.getSavedCards();
    }

    @Override
    public void showCards(List<Card> cards) {
        mCardAdapter.setCardData(cards);
        mCardAdapter.notifyDataSetChanged();
    }
}
