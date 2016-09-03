package com.dkjs.fitness.biz;

import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.domain.UserAct;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by administrator on 16/7/30.
 */
public interface IFTActivityBiz {

    void publishAct(FTActivity ftActivity, PublishActivityListener paListener);

    void queryLastestActs(@NotNull QueryActivityListener qaListener);

    void removeAct(@NotNull FTActivity act, RemoveActivityListener raListener);
    void removeAct(@NotNull String shuoShuoId, RemoveActivityListener raListener);

    void joinInAct(@NotNull FTActivity act, @NotNull String userId, String nickname,
                   String portrait, JoinQuitActListener jqListener);
    void quitAct(@NotNull FTActivity act, @NotNull String userId, JoinQuitActListener jqListener);

    void queryActMember(String shuoShuoId, QueryMemberListener listener);

    interface PublishActivityListener{
        String STEP_UPLOAD_FILE = "上传文件";
        String STEP_UPLOAD_ACT = "发布状态";

        void onSuccess();
        void onFailure(String msg);
        void onProgress(String step, int i);
    }

    interface QueryActivityListener{
        void onSuccess(List<FTActivity> actList);
        void onFailure(String msg);
    }

    interface RemoveActivityListener{
        void onSuccess();
        void onFailure(String msg);
    }

    interface JoinQuitActListener{
        void onSuccess();
        void onFailure(String msg);
    }

    interface QueryMemberListener{
        void onSuccess(List<UserAct> userList);
        void onFailure(String msg);
    }



}
