package com.dkjs.fitness.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.comm.GridItemDecoration;
import com.dkjs.fitness.domain.State;
import com.dkjs.fitness.domain.StateTest;
import com.dkjs.fitness.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/7/10.
 */
public class FindHotFragment extends FitnessFragment {

    public static final int BANNER_SWITCH_INTERVAL = 5000;

    public static final int LOADAD_DATA = 0;


    ConvenientBanner mCBanner;
    SwipeRefreshLayout mFindHotRL;
    RecyclerView mFindHotRV;

    List<String> bannerImgList;
    List<StateTest> stateList;
    StateAdapter stateAdapter;

    Handler mUIHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUIHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case LOADAD_DATA:
                        mFindHotRL.setRefreshing(false);
                        stateAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };


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
        mFindHotRL = (SwipeRefreshLayout) view.findViewById(R.id.srl_find_hot);
        mFindHotRV = (RecyclerView) view.findViewById(R.id.rv_find_hot);

        loadData();

        initRecylerView();

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
                }).setPageIndicator(new int[]{R.drawable.ic_page_indicator,
                R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setManualPageable(true);   //设置可以手动滑动

        mFindHotRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUIHandler.sendEmptyMessageAtTime(LOADAD_DATA, SystemClock.uptimeMillis() + 2000);
            }
        });


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

    private void loadData() {
        bannerImgList = new ArrayList<String>();
        bannerImgList.add("http://img1.imgtn.bdimg.com/it/u=948827144,1859327675&fm=21&gp=0.jpg");
        bannerImgList.add("http://img3.imgtn.bdimg.com/it/u=4076258573,3568489988&fm=21&gp=0.jpg");
        bannerImgList.add("http://img2.imgtn.bdimg.com/it/u=1414262505,3566931615&fm=21&gp=0.jpg");
        bannerImgList.add("http://img4.imgtn.bdimg.com/it/u=2086434209,3926558397&fm=21&gp=0.jpg");
        bannerImgList.add("http://img5.imgtn.bdimg.com/it/u=2843390349,4251941052&fm=21&gp=0.jpg");
        bannerImgList.add("http://img5.imgtn.bdimg.com/it/u=2679161897,2271446926&fm=21&gp=0.jpg");

        stateList = new ArrayList<StateTest>();
        for (int i = 0; i < 25; i++) {
            StateTest state = new StateTest();
            if (i % 3 == 0) {
                state.setSourceUrl("http://img0.imgtn.bdimg.com/it/u=2377301932,2910798798&fm=21&gp=0.jpg");
            } else if (i % 3 == 1) {
                state.setSourceUrl("http://img0.imgtn.bdimg.com/it/u=744815246,3640932668&fm=21&gp=0.jpg");
            } else {
                state.setSourceUrl("http://img0.imgtn.bdimg.com/it/u=3174997060,1739124222&fm=21&gp=0.jpg");
            }
            stateList.add(state);

        }
    }

    private void initRecylerView() {
        stateAdapter = new StateAdapter(stateList);
        mFindHotRV.setAdapter(stateAdapter);
        mFindHotRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFindHotRV.addItemDecoration(new GridItemDecoration(mContext));
    }

    public class NetworkImageHolderView implements Holder<String> {

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


    /**
     * 状态 adapter
     */
    public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

        private List<StateTest> datas;

        public StateAdapter(List<StateTest> datas) {
            this.datas = datas;
        }

        @Override
        public StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StateViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.item_state, null));
        }

        @Override
        public void onBindViewHolder(StateViewHolder holder, int position) {
            holder.stateImg.setImageURI(datas.get(position).getSourceUrl());

        }

        @Override
        public int getItemCount() {
            if (datas == null) return 0;
            return datas.size();
        }

        public class StateViewHolder extends RecyclerView.ViewHolder {
            SimpleDraweeView stateImg;
            ImageView praiseIV, commentIV;
            TextView praiseTV, commentTV;

            public StateViewHolder(View itemView) {
                super(itemView);
                stateImg = (SimpleDraweeView) itemView.findViewById(R.id.item_state_img);
                praiseIV = (ImageView) itemView.findViewById(R.id.iv_praise);
                commentIV = (ImageView) itemView.findViewById(R.id.iv_comment);
                praiseTV = (TextView) itemView.findViewById(R.id.tv_praise_count);
                commentTV = (TextView) itemView.findViewById(R.id.tv_comment_count);

            }
        }
    }

}
