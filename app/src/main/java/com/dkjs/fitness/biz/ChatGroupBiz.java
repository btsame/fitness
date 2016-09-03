package com.dkjs.fitness.biz;

import com.dkjs.fitness.comm.FitnessApplication;
import com.maxleap.im.DataHandler;
import com.maxleap.im.DataListHandler;
import com.maxleap.im.ParrotException;
import com.maxleap.im.SimpleDataHandler;
import com.maxleap.im.entity.Group;
import com.maxleap.im.entity.Message;
import com.maxleap.im.entity.MessageBuilder;
import com.maxleap.im.entity.MessageHistory;

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

    @Override
    public void queryChatRecord(String groupId, long timestamp, final int limit, final GroupHandlerListener<List<MessageHistory>> listener) {
//        groupId = "\"" + groupId + "\"";
        FitnessApplication.getmGlobalParrot().recentGroupMessages(groupId, timestamp, limit,
                new DataListHandler<MessageHistory>() {
                    @Override
                    public void onSuccess(List<MessageHistory> list) {
                        if(listener != null){
                            listener.onSuccess(list);
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
    public void sendTextMessage(String groupId, String msg, final GroupHandlerListener<Void> listener) {
        Message message = MessageBuilder.newBuilder()
                .toGroup(groupId)
                .text(msg);
        FitnessApplication.getmGlobalParrot().sendMessage(message, new SimpleDataHandler<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(listener != null){
                    listener.onSuccess(aVoid);
                }
            }
        });
    }

    @Override
    public void sendImgMessage(final String groupId, String imgPath, final GroupHandlerListener<Void> listener) {
        IFileBiz fileBiz = new FileBiz();
        fileBiz.uploadFile(imgPath, new IFileBiz.FileUploadListener() {
            @Override
            public void onSucess(String url) {
                Message message = MessageBuilder.newBuilder()
                        .toGroup(groupId)
                        .image(url);
                FitnessApplication.getmGlobalParrot().sendMessage(message, new SimpleDataHandler<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(listener != null){
                            listener.onSuccess(aVoid);
                        }
                    }
                });
            }

            @Override
            public void onFailure(String reason) {
                if(listener != null){
                    listener.onFailure(reason);
                }
            }

            @Override
            public void onProgress(int i) {

            }
        });
    }
}
