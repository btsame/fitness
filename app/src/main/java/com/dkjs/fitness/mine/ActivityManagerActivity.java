package com.dkjs.fitness.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.dkjs.fitness.R;


public class ActivityManagerActivity extends FragmentActivity {
    TabLayout mTabLayout;
    ViewPager mVP;
    Fragment[] fragments;
    String[] tabNames;
    FragActivityAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manager_party_list_item);
       // initView();
        //initFragment();

    }
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tl_frag_find);
        mVP = (ViewPager) findViewById(R.id.vp_frag_find);
    }
    private void initFragment(){
        fragments = new Fragment[2];
        fragments[0] = new ActivityManagerDoingFragement();
        fragments[1] = new ActivityManagerFinishFragment();
        tabNames = new String[]{"进行中", "已完成"};

        mPagerAdapter = new FragActivityAdapter(getSupportFragmentManager(), fragments, tabNames);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mVP.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mVP);
    }


    /**
     * ViewPager adapter
     */
    public static class FragActivityAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;
        String[] titles;

        public FragActivityAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
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
        public int getCount() {return fragments.length;}
    }
}
