package com.dkjs.fitness.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;

/**
 * Created by administrator on 16/7/10.
 */
public class FindFragment extends FitnessFragment {

    TabLayout mTabLayout;
    ViewPager mVP;
    Fragment[] fragments;
    String[] tabNames;

    FragFindPagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragment();

    }

    private void initFragment(){
        fragments = new Fragment[2];
//        fragments[0] = new FindHotFragment();
//        fragments[1] = new FindActivityFragment();
        fragments[0] = new FindHotActivityFragment();
        fragments[1] = new FindActivityFragment2();
        tabNames = new String[]{"热门", "活动"};
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayout = (TabLayout) view.findViewById(R.id.tl_frag_find);
        mVP = (ViewPager) view.findViewById(R.id.vp_frag_find);


        mPagerAdapter = new FragFindPagerAdapter(getChildFragmentManager(), fragments, tabNames);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mVP.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mVP);

    }


    /**
     * ViewPager adapter
     */
    public static class FragFindPagerAdapter extends FragmentStatePagerAdapter{

        Fragment[] fragments;
        String[] titles;


        public FragFindPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

}
