package com.ofalvai.aircard.presentation.mycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyCardsActivity extends AppCompatActivity implements
        MyCardsContract.View,
        CardEditFragment.OnFragmentInteractionListener,
        CardColorFragment.OnFragmentInteractionListener {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    private MyCardsAdapter mMyCardsAdapter;

    @BindView(R.id.my_cards_list)
    RecyclerView mMyCardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        ButterKnife.bind(this);

        mPresenter = new MyCardsPresenter(MyCardsActivity.this);
        mPresenter.attachView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCardList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_cards, menu);
        return true;
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
        CardEditFragment fragment = CardEditFragment.newInstance(CardEditFragment.INVOKE_MODE_CREATE);
        fragment.show(getSupportFragmentManager(), CardEditFragment.TAG);
    }

    private void initCardList() {
        if (mMyCardsAdapter == null) {
            mMyCardsAdapter = new MyCardsAdapter(mPresenter, MyCardsActivity.this);
            mMyCardsList.setAdapter(mMyCardsAdapter);
            mMyCardsList.setLayoutManager(new LinearLayoutManager(MyCardsActivity.this));
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
        CardColorFragment fragment = CardColorFragment.newInstance(card);
        fragment.show(getSupportFragmentManager(), CardColorFragment.TAG);
    }
}
