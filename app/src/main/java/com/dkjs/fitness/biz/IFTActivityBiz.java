package com.dkjs.fitness.biz;

import com.dkjs.fitness.domain.FTActivity;

/**
 * Created by administrator on 16/7/30.
 */
public interface IFTActivityBiz {

    void publishAct(FTActivity ftActivity, PublishActivityListener paListener);

    interface PublishActivityListener{
        String STEP_UPLOAD_FILE = "上传文件";
        String STEP_UPLOAD_ACT = "发布状态";

        void onSucess();
        void onFailure(String msg);
        void onProgress(String step, int i);
    }

}
