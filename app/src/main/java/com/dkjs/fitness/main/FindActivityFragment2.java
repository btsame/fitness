package com.dkjs.fitness.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.act.ActShowActivity;
import com.dkjs.fitness.biz.FTActivityBiz;
import com.dkjs.fitness.biz.IFTActivityBiz;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.comm.LinearItemDecoration;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.util.ToastUtils;
import com.dkjs.fitness.util.URIUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by administrator on 16/7/10.
 */
public class FindActivityFragment2 extends FitnessFragment {

    public static final int LOADAD_DATA = 0;

    SwipeRefreshLayout mFindActRL;
    RecyclerView mFindActRV;

    List<FTActivity> activityList;
    ActivityAdapter activityAdapter;

    IFTActivityBiz iftActivityBiz;
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
                        activityAdapter.setDatas(activityList);
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

        mFindActRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        iftActivityBiz = new FTActivityBiz();
        loadData();
        initRecylerView();

    }

    private void loadData(){
        /*activityList = new ArrayList<FTActivity>();
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

        }*/

        iftActivityBiz.queryLastestActs(new IFTActivityBiz.QueryActivityListener() {
            @Override
            public void onSuccess(List<FTActivity> actList) {
                if (activityList != null) {
                    activityList.clear();
                }
                activityList = actList;
                mUIHandler.sendEmptyMessage(LOADAD_DATA);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showCustomToast(mContext, msg);
                mUIHandler.sendEmptyMessage(LOADAD_DATA);
            }
        });
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

        public void setDatas(List<FTActivity> datas) {
            this.datas = datas;
        }

        @Override
        public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ActivityViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.layout_allparty_list_item, null));
        }

        @Override
        public void onBindViewHolder(ActivityViewHolder holder, final int position) {

            if(datas.get(position).getOwner() != null){
                if(datas.get(position).getOwner().getPortrait() != null){
                    holder.actOwnerPortrait.setImageURI(
                            URIUtil.handleNetworkUri(datas.get(position).getOwner().getPortrait()));
                }
                holder.actOwnerNickname.setText(
                        datas.get(position).getOwner().getNickName()
                );
            }


            holder.actSubject.setText(datas.get(position).getSubject());
            holder.actTime.setText(datas.get(position).getBeginTime() + "到" +
            datas.get(position).getEndTime());
            holder.actAddress.setText(datas.get(position).getAddress());
            holder.actPrice.setText(
                    getString(R.string.act_price, datas.get(position).getPrice())
            );

            holder.actThumbnail.setImageURI(
                    URIUtil.handleNetworkUri(datas.get(position).getSourceUrl())
            );

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActShowActivity.class);
                    intent.putExtra(ActShowActivity.EXTRA_ACT_DATA, datas.get(position));
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            if(datas == null) return 0;
            return datas.size();
        }

        public class ActivityViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.sdv_item_act_portrait)
            SimpleDraweeView actOwnerPortrait;
            @Bind(R.id.tv_item_act_nickname)
            TextView actOwnerNickname;
            @Bind(R.id.tv_item_act_subject)
            TextView actSubject;
            @Bind(R.id.tv_item_act_time)
            TextView actTime;
            @Bind(R.id.tv_item_act_address)
            TextView actAddress;
            @Bind(R.id.tv_item_act_price)
            TextView actPrice;
            @Bind(R.id.sdv_item_act_thumbnail)
            SimpleDraweeView actThumbnail;

            public ActivityViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
