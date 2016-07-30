package com.dkjs.fitness.biz;

import com.dkjs.fitness.domain.FTActivity;

/**
 * Created by administrator on 16/7/30.
 */
public interface IFTActivityBiz {

    boolean publishAct(FTActivity ftActivity, PublishActivityListener paListener);

    interface PublishActivityListener{
        void onSucess();
        void onFailure(String msg);
        void onProgress(int i);
    }

}
