package com.dkjs.fitness.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends FitnessActivity implements OnTabItemSelectListener{

    ViewPager mMainVP;
    PagerBottomTabLayout mMainPBTL;


    Controller mPagerBottomTagController;

    Fragment[] fragmentArr = new Fragment[]{
      new TrainFragment(), new FindFragment(), new MineFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();

    }

    @Override
    protected void initView() {
        super.initView();

        mMainVP = (ViewPager) findViewById(R.id.vp_main);
        mMainPBTL = (PagerBottomTabLayout) findViewById(R.id.pbtl_main);
        mMainVP.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initPagerBottomTab();

    }

    @Override
    protected void setListener() {
        super.setListener();

        mMainVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerBottomTagController.setSelect(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPagerBottomTab(){

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_gallery)
                .setText("训练")
                .setSelectedColor(getResources().getColor(R.color.tab_color_1))
                .build();

        mPagerBottomTagController = mMainPBTL.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(android.R.drawable.ic_menu_search,
                        "发现", getResources().getColor(R.color.tab_color_2))
                .addTabItem(android.R.drawable.ic_menu_myplaces,
                        "我的", getResources().getColor(R.color.tab_color_3))
                .setMode(TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

        mPagerBottomTagController.addTabItemClickListener(this);
    }


    @Override
    public void onSelected(int index, Object tag) {
        mMainVP.setCurrentItem(index);

    }

    @Override
    public void onRepeatClick(int index, Object tag) {

    }

    public void onBackPressed() {
        exitApp();
    }

    /**
     * 主界面ViewPager的Adapter
     */
    public class MyPagerAdapter extends FragmentStatePagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArr[position];
        }

        @Override
        public int getCount() {
            return fragmentArr.length;
        }


    }
}
