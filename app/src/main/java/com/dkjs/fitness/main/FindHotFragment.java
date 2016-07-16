package com.dkjs.fitness.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/7/10.
 */
public class FindHotFragment extends FitnessFragment {

    public static final int BANNER_SWITCH_INTERVAL = 5000;

    ConvenientBanner mCBanner;
    RecyclerView mFindHotRV;

    List<String> bannerImgList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_hot, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCBanner = (ConvenientBanner) view.findViewById(R.id.cban_find_hot);
        mFindHotRV = (RecyclerView) view.findViewById(R.id.rv_find_hot);

        loadData();

        mCBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerImgList)
        .setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showCustomToast(getActivity(), "点击" + position + "个");

            }
        })
        .setManualPageable(true);   //设置可以手动滑动


    }

    @Override
    public void onResume() {
        super.onResume();
        mCBanner.startTurning(BANNER_SWITCH_INTERVAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCBanner.stopTurning();
    }

    private void loadData(){
        bannerImgList = new ArrayList<String>();
        bannerImgList.add("http://img1.imgtn.bdimg.com/it/u=948827144,1859327675&fm=21&gp=0.jpg");
        bannerImgList.add("http://img3.imgtn.bdimg.com/it/u=4076258573,3568489988&fm=21&gp=0.jpg");
        bannerImgList.add("http://img2.imgtn.bdimg.com/it/u=1414262505,3566931615&fm=21&gp=0.jpg");
        bannerImgList.add("http://img4.imgtn.bdimg.com/it/u=2086434209,3926558397&fm=21&gp=0.jpg");
        bannerImgList.add("http://img5.imgtn.bdimg.com/it/u=2843390349,4251941052&fm=21&gp=0.jpg");
        bannerImgList.add("http://img5.imgtn.bdimg.com/it/u=2679161897,2271446926&fm=21&gp=0.jpg");
    }

    public class NetworkImageHolderView implements Holder<String>{

        private SimpleDraweeView simpleDraweeView;

        @Override
        public View createView(Context context) {
            simpleDraweeView =
                    (SimpleDraweeView) LayoutInflater.from(context).inflate(R.layout.activity_banner_item, null);
            return simpleDraweeView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Uri uri = Uri.parse(data);

            simpleDraweeView.setImageURI(uri);
        }
    }



}
