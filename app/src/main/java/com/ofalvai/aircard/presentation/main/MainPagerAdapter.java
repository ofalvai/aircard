package com.ofalvai.aircard.presentation.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.mycards.MyCardsFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 1;

    private final Context mContext;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = MyCardsFragment.newInstance();
                break;
            default:
                fragment = new Fragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch (position) {
            case 0:
                title = mContext.getString(R.string.tab_title_my_cards);
                break;
            default:
                title = "Default tab";
        }

        return title;
    }
}
