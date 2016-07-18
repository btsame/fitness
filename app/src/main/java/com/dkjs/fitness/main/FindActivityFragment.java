package com.dkjs.fitness.main;

import android.graphics.Canvas;
import android.graphics.Rect;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.comm.LinearItemDecoration;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.StateTest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/7/10.
 */
public class FindActivityFragment extends FitnessFragment {

    public static final int LOADAD_DATA = 0;

    SwipeRefreshLayout mFindActRL;
    RecyclerView mFindActRV;
    TextView mTitleTV;

    List<FTActivity> activityList;
    ActivityAdapter activityAdapter;

    Handler mUIHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUIHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case LOADAD_DATA:
                        mFindActRL.setRefreshing(false);
                        activityAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_activity, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFindActRL = (SwipeRefreshLayout) view.findViewById(R.id.srl_find_activity);
        mFindActRV = (RecyclerView) view.findViewById(R.id.rv_find_activity);
        mTitleTV = (TextView) view.findViewById(R.id.tv_act_title);

        mFindActRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUIHandler.sendEmptyMessageAtTime(LOADAD_DATA, SystemClock.uptimeMillis() + 2000);
            }
        });

        loadData();
        initRecylerView();

    }

    private void loadData(){
        activityList = new ArrayList<FTActivity>();
        for(int i = 0; i < 25; i++){
            FTActivity activity = new FTActivity();
            if(i % 3 == 0){
                activity.setSourceUrl("http://img4.imgtn.bdimg.com/it/u=4116041475,1110485249&fm=21&gp=0.jpg");
            }else if(i % 3 == 1){
                activity.setSourceUrl("http://img5.imgtn.bdimg.com/it/u=234513892,309969409&fm=21&gp=0.jpg");
            }else {
                activity.setSourceUrl("http://img4.imgtn.bdimg.com/it/u=2807917767,1550638337&fm=21&gp=0.jpg");
            }
            activityList.add(activity);

        }
    }

    private void initRecylerView(){
        activityAdapter = new ActivityAdapter(activityList);
        mFindActRV.setAdapter(activityAdapter);
        mFindActRV.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        mFindActRV.addItemDecoration(new LinearItemDecoration(mContext, LinearLayoutManager.VERTICAL));
    }


    /**
     * 状态 adapter
     */
    public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>{

        private List<FTActivity> datas;

        public ActivityAdapter(List<FTActivity> datas) {
            this.datas = datas;
        }

        @Override
        public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ActivityViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.item_activity, null));
        }

        @Override
        public void onBindViewHolder(ActivityViewHolder holder, int position) {
            holder.stateImg.setImageURI(datas.get(position).getSourceUrl());

        }

        @Override
        public int getItemCount() {
            if(datas == null) return 0;
            return datas.size();
        }

        public class ActivityViewHolder extends RecyclerView.ViewHolder{
            SimpleDraweeView stateImg;
            ImageView praiseIV, commentIV;
            TextView praiseTV, commentTV;

            public ActivityViewHolder(View itemView) {
                super(itemView);
                stateImg = (SimpleDraweeView)itemView.findViewById(R.id.item_activity_img);
                praiseIV = (ImageView)itemView.findViewById(R.id.iv_praise);
                commentIV = (ImageView)itemView.findViewById(R.id.iv_comment);
                praiseTV = (TextView)itemView.findViewById(R.id.tv_praise_count);
                commentTV = (TextView)itemView.findViewById(R.id.tv_comment_count);

            }
        }
    }


}
