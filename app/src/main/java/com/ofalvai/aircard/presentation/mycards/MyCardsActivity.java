package com.ofalvai.aircard.presentation.mycards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.model.Card;

import java.util.List;

import butterknife.ButterKnife;


public class MyCardsActivity extends AppCompatActivity implements MyCardsContract.View {

    @Nullable
    private MyCardsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        ButterKnife.bind(this);

        mPresenter = new MyCardsPresenter(MyCardsActivity.this);
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
    }

    @Override
    public void showCards(List<Card> cards) {

    }
}
