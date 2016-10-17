package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyCardsActivity extends AppCompatActivity implements NearbyCardsContract.View {

    @Nullable
    private NearbyCardsContract.Presenter mPresenter;

    private NearbyCardAdapter mNearbyCardAdapter;

    @BindView(R.id.nearby_cards_list)
    RecyclerView mNearbyCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_cards);

        mPresenter = new NearbyCardsPresenter(NearbyCardsActivity.this);
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

        ButterKnife.bind(this);

        initCardList();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initCardList() {
        if (mNearbyCardAdapter == null) {
            mNearbyCardAdapter = new NearbyCardAdapter(mPresenter, NearbyCardsActivity.this);
            mNearbyCardList.setAdapter(mNearbyCardAdapter);
            //mNearbyCardList.setLayoutManager(new LinearLayoutManager(NearbyCardsActivity.this));
            mNearbyCardList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        }

        mPresenter.getTestCards();
    }

    @Override
    protected void onDestroy() {
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
    public void showError(String message) {

    }

    @Override
    public void showWarning(String message) {

    }

    @Override
    public void showMessageCardAdded() {
        Snackbar.make(mNearbyCardList, getString(R.string.message_card_saved), Snackbar.LENGTH_SHORT).show();
    }
}
