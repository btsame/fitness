package com.dkjs.fitness.biz;

import com.maxleap.im.entity.Group;

import java.util.List;

/**
 * Created by administrator on 16/8/13.
 */
public interface IChatGroupBiz {
    void createGroup(String name, List<String> userIds,
                     GroupHandlerListener<Group> listener);

    void deleteGroup(String groupId, GroupHandlerListener<Void> listener);

    void addGroupMember(String groupId, String userId,
                        GroupHandlerListener<Void> listener);

    void removeGroupMember(String groupId, String userId,
                           GroupHandlerListener<Void> listener);



    interface GroupHandlerListener<T>{
        void onSuccess(T t);
        void onFailure(String errorCode);
    }
}
