package com.dkjs.fitness.biz;

import com.dkjs.fitness.comm.FitnessApplication;
import com.maxleap.im.DataHandler;
import com.maxleap.im.ParrotException;
import com.maxleap.im.entity.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/8/13.
 */
public class ChatGroupBiz implements IChatGroupBiz{
    @Override
    public void createGroup(String name, List<String> userIds, final GroupHandlerListener<Group> listener) {
        FitnessApplication.getmGlobalParrot().createGroup(name, userIds, new DataHandler<Group>() {
            @Override
            public void onSuccess(com.maxleap.im.entity.Group group) {
                if(listener != null){
                    listener.onSuccess(group);
                }
            }

            @Override
            public void onError(ParrotException e) {
                if(listener != null){
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteGroup(String groupId, final GroupHandlerListener<Void> listener) {
        FitnessApplication.getmGlobalParrot().deleteGroup(groupId, new DataHandler<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(listener != null){
                    listener.onSuccess((Void) null);
                }
            }

            @Override
            public void onError(ParrotException e) {
                if(listener != null){
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }

    @Override
    public void addGroupMember(String groupId, String userId, final GroupHandlerListener<Void> listener) {
        List<String> userIdList = new ArrayList<String>();
        userIdList.add(userId);
        FitnessApplication.getmGlobalParrot().addGroupMembers(groupId, userIdList, new DataHandler<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(listener != null){
                    listener.onSuccess(null);
                }
            }

            @Override
            public void onError(ParrotException e) {
                if(listener != null){
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }

    @Override
    public void removeGroupMember(String groupId, String userId, final GroupHandlerListener<Void> listener) {
        List<String> userIdList = new ArrayList<String>();
        userIdList.add(userId);
        FitnessApplication.getmGlobalParrot().removeGroupMembers(groupId, userIdList, new DataHandler<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(listener != null){
                    listener.onSuccess(null);
                }
            }

            @Override
            public void onError(ParrotException e) {
                if(listener != null){
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }
}
