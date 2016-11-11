package com.ofalvai.aircard.presentation.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ofalvai.aircard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Nullable
    private MainPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        if (mViewPager != null) {
            mViewPager.setAdapter(mPagerAdapter);
        }

        if (mTabLayout != null && mViewPager != null && mViewPager.getAdapter() != null) {
            mTabLayout.setupWithViewPager(mViewPager, false);
        }
    }
}
