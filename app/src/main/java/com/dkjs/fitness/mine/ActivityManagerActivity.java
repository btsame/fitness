package com.dkjs.fitness.mine;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.chat.ChatRoomActivity;
import com.dkjs.fitness.comm.FitnessActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ActivityManagerActivity extends FitnessActivity {
    TabLayout mTabLayout;
    ViewPager mVP;
    Fragment[] fragments;
    String[] tabNames;
    FragActivityAdapter mPagerAdapter;

    @Bind(R.id.tv_sign_state)
    TextView mChatRoomTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manager_party_list_item);

        ButterKnife.bind(this);

        mChatRoomTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ChatRoomActivity.class));
            }
        });
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
