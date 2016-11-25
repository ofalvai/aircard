package com.ofalvai.aircard.presentation.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.presentation.mycards.MyCardsFragment;
import com.ofalvai.aircard.presentation.nearbycards.NearbyCardsFragment;
import com.ofalvai.aircard.presentation.savedcards.SavedCardsFragment;

public class MainPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private static final int PAGE_COUNT = 3;

    private static final int PAGE_INDEX_MY_CARDS = 0;

    private static final int PAGE_INDEX_NEARBY_CARDS = 1;

    private static final int PAGE_INDEX_SAVED_CARDS = 2;

    private final Context mContext;

    private SparseArray<Fragment> mRegisteredFragments = new SparseArray<>();

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case PAGE_INDEX_MY_CARDS:
                fragment = MyCardsFragment.newInstance();
                break;
            case PAGE_INDEX_NEARBY_CARDS:
                fragment = NearbyCardsFragment.newInstance();
                break;
            case PAGE_INDEX_SAVED_CARDS:
                fragment = SavedCardsFragment.newInstance();
                break;
            default:
                fragment = new Fragment();
        }

        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch (position) {
            case PAGE_INDEX_MY_CARDS:
                title = mContext.getString(R.string.tab_title_my_cards);
                break;
            case PAGE_INDEX_NEARBY_CARDS:
                title = mContext.getString(R.string.tab_title_nearby_cards);
                break;
            case PAGE_INDEX_SAVED_CARDS:
                title = mContext.getString(R.string.tab_title_saved_cards);
                break;
            default:
                title = "Default tab";
        }

        return title;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == PAGE_INDEX_SAVED_CARDS) {
            // Refreshing the saved cards list every time it becomes active. This is needed because
            // new cards might have been saved from the nearby cards list.
            SavedCardsFragment fragment = (SavedCardsFragment) mRegisteredFragments.get(position);
            if (fragment != null) {
                fragment.refreshSavedCards();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
