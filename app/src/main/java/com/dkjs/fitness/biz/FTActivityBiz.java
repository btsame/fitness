package com.dkjs.fitness.biz;

import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.domain.UserAct;
import com.dkjs.fitness.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxleap.DeleteCallback;
import com.maxleap.FindCallback;
import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.MLUser;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;
import com.maxleap.im.entity.Group;
import com.maxleap.social.CommentManager;
import com.maxleap.social.DataHandler;
import com.maxleap.social.DataListHandler;
import com.maxleap.social.HermsException;
import com.maxleap.social.MLHermes;
import com.maxleap.social.ShuoShuoManager;
import com.maxleap.social.entity.Comment;
import com.maxleap.social.entity.Constraint;
import com.maxleap.social.entity.ShuoShuo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/7/30.
 */
public class FTActivityBiz implements IFTActivityBiz {

    public static final String TAG = "FTActivityBiz";

    ShuoShuoManager manager;
    CommentManager commentManager;
    IChatGroupBiz chatGroupBiz;

    public FTActivityBiz() {
        manager = MLHermes.getShuoShuoManager();
        commentManager = MLHermes.getCommentManager();
        chatGroupBiz = new ChatGroupBiz();
    }

    @Override
    public void publishAct(final FTActivity ftActivity, final PublishActivityListener paListener) {


        IFileBiz fileBiz = new FileBiz();
        fileBiz.uploadFile(ftActivity.getSourceUrl(), new IFileBiz.FileUploadListener() {
            @Override
            public void onSucess(final String url) {
                ftActivity.setSourceUrl(url);

                List<String> userIdList = new ArrayList<String>();
                userIdList.add(GlobalUserManager.getUserId());
                chatGroupBiz.createGroup(ftActivity.getSubject(), userIdList,
                        new IChatGroupBiz.GroupHandlerListener<Group>() {
                            @Override
                            public void onSuccess(Group group) {
                                String groupId = group.getId();
                                if (groupId.startsWith("\"")) {
                                    groupId = groupId.substring(1, groupId.length() - 1);
                                }
                                ftActivity.setGroupId(groupId);

                                Gson gson = new Gson();
                                String actDetail = gson.toJson(ftActivity);
                                LogUtil.e(TAG, "actDetail:" + actDetail);

                                ShuoShuo shuoShuo = new ShuoShuo();
                                shuoShuo.setUserId(GlobalUserManager.getUserId());
                                shuoShuo.setContent(actDetail);
                                manager.createOrUpdateShuoShuo(shuoShuo, new DataHandler<String>() {
                                    @Override
                                    public void onSuccess(String objectId) {
                                        if (paListener != null) {
                                            paListener.onProgress(paListener.STEP_UPLOAD_FILE, 100);
                                            paListener.onSuccess();
                                        }

                                    }

                                    @Override
                                    public void onError(HermsException e) {
                                        if (paListener != null) {
                                            paListener.onFailure(e.getMessage());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(String errorCode) {
                                if (paListener != null) {
                                    paListener.onFailure(errorCode);
                                }
                            }
                        });
            }

            @Override
            public void onFailure(String reason) {
                if (paListener != null) {
                    paListener.onFailure(reason);
                }
            }

            @Override
            public void onProgress(int i) {
                if (paListener != null) {
                    paListener.onProgress(paListener.STEP_UPLOAD_FILE, (int) (i * 0.9));
                }
            }
        });


    }


    @Override
    public void queryLastestActs(@NotNull final QueryActivityListener qaListener) {
        Constraint constraint = new Constraint.Builder()
                .orderByTime()
                .desc()
                .build();

        manager.getLatestShuoShuos(constraint, new DataListHandler<ShuoShuo>() {
            @Override
            public void onSuccess(List<ShuoShuo> list) {
                List<FTActivity> actList = new ArrayList<FTActivity>();
                for (ShuoShuo temp : list) {
                    try {
//                        removeAct(temp.getObjectId(), null);
                        String content = temp.getContent();
                        content = content.replaceAll("\\\\", "\"");
                        LogUtil.e(TAG, "*************************content:" + content);
                        Gson gson = new Gson();
                        FTActivity act = gson.fromJson(content, FTActivity.class);
                        act.setShuoShuo(temp);
                        actList.add(act);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                qaListener.onSuccess(actList);
            }

            @Override
            public void onError(HermsException e) {
                qaListener.onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void removeAct(@NotNull final FTActivity actList, final RemoveActivityListener raListener) {
        chatGroupBiz.deleteGroup(actList.getGroupId(), new IChatGroupBiz.GroupHandlerListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                manager.deleteShuoShuo(actList.getShuoShuo().getObjectId(), new DataHandler<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        if (raListener != null) {
                            raListener.onSuccess();
                        }
                    }

                    @Override
                    public void onError(HermsException e) {
                        if (raListener != null) {
                            raListener.onFailure(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onFailure(String errorCode) {
                if (raListener != null) {
                    raListener.onFailure(errorCode);
                }
            }
        });

    }

    @Override
    public void removeAct(@NotNull final String shuoShuoId, final RemoveActivityListener raListener) {
        manager.getShuoShuo(shuoShuoId, new DataHandler<ShuoShuo>() {
            @Override
            public void onSuccess(ShuoShuo shuoShuo) {
                String content = shuoShuo.getContent();
                content = content.replaceAll("\\\\", "\"");
                LogUtil.e(TAG, "*************************content:" + content);
                Gson gson = new Gson();
                FTActivity act = gson.fromJson(content, FTActivity.class);
                chatGroupBiz.deleteGroup(act.getGroupId(), new IChatGroupBiz.GroupHandlerListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        manager.deleteShuoShuo(shuoShuoId, new DataHandler<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                if (raListener != null) {
                                    raListener.onSuccess();
                                }
                            }

                            @Override
                            public void onError(HermsException e) {
                                if (raListener != null) {
                                    raListener.onFailure(e.getMessage());
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        if (raListener != null) {
                            raListener.onFailure(errorCode);
                        }
                    }
                });
            }

            @Override
            public void onError(HermsException e) {
                if (raListener != null) {
                    raListener.onFailure(e.getMessage());
                }
            }
        });

    }

    /**
     * 前期在客户端实现创建，后期应该放在云端，这样会有事务
     *
     * @param act
     * @param userId
     * @param jqListener
     */
    @Override
    public void joinInAct(@NotNull final FTActivity act, @NotNull final String userId,
                          String nickName, String portrait, final JoinQuitActListener jqListener) {
//        commentManager.favoriteComment(userId, actId, new DataHandler<String>() {
//            @Override
//            public void onSuccess(String s) {
//                UserAct userAct = new UserAct();
//                userAct.setActId(actId);
//                userAct.setUserId(userId);
//                MLDataManager.saveInBackground(userAct, new SaveCallback() {
//                    @Override
//                    public void done(MLException e) {
//                        if (e == null) {
//                            if (jqListener != null) {
//                                jqListener.onSuccess();
//                            }
//                        } else {
//                            if (jqListener != null) {
//                                jqListener.onFailure(e.getMessage());
//                            }
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onError(HermsException e) {
//                if (jqListener != null) {
//                    jqListener.onFailure(e.getMessage());
//                }
//            }
//        });

        UserAct userAct = new UserAct();
        userAct.setActId(act.getShuoShuo().getObjectId());
        userAct.setUserId(userId);
        userAct.setNickname(nickName);
        userAct.setPortrait(portrait);
        MLDataManager.saveInBackground(userAct, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    chatGroupBiz.addGroupMember(act.getGroupId(), userId, new IChatGroupBiz.GroupHandlerListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (jqListener != null) {
                                jqListener.onSuccess();
                            }
                        }

                        @Override
                        public void onFailure(String errorCode) {
                            if (jqListener != null) {
                                jqListener.onFailure(errorCode);
                            }
                        }
                    });

                } else {
                    if (jqListener != null) {
                        jqListener.onFailure(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void quitAct(@NotNull final FTActivity act, @NotNull final String userId, final JoinQuitActListener jqListener) {
//        commentManager.getComments(actId, new DataListHandler<Comment>() {
//            @Override
//            public void onSuccess(List<Comment> list) {
//                for (Comment temp : list) {
//                    if (temp.isZan()) {
//                        if (userId.equals(temp.getUserId())) {
//                            commentManager.deleteComment(temp.getObjectId(), new DataHandler<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    removeUserAct(actId, userId, jqListener);
//                                }
//
//                                @Override
//                                public void onError(HermsException e) {
//                                    if (jqListener != null) {
//                                        jqListener.onFailure(e.getMessage());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(HermsException e) {
//                if (jqListener != null) {
//                    jqListener.onFailure(e.getMessage());
//                }
//            }
//        });
        removeUserAct(act, userId, jqListener);


    }

    @Override
    public void queryActMember(String shuoShuoId, final QueryMemberListener listener) {
        MLQuery<UserAct> mlQuery = MLQuery.getQuery(UserAct.class);
        mlQuery.whereEqualTo("actId", shuoShuoId);
        MLQueryManager.findAllInBackground(mlQuery, new FindCallback<UserAct>() {
            @Override
            public void done(List<UserAct> list, MLException e) {
                if(e != null){
                    if(listener != null){
                        listener.onFailure(e.getMessage());
                    }
                }else{
                    if(listener != null){
                        listener.onSuccess(list);
                    }
                }
            }
        });
    }

    private void removeUserAct(final FTActivity act, final String userId, final JoinQuitActListener jqListener) {
        chatGroupBiz.removeGroupMember(act.getGroupId(), userId, new IChatGroupBiz.GroupHandlerListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                MLQuery<UserAct> userActMLQuery = MLQuery.getQuery(UserAct.class);
                userActMLQuery.whereEqualTo("actId", act.getShuoShuo().getObjectId());
                userActMLQuery.whereEqualTo("userId", userId);
                MLQueryManager.getFirstInBackground(userActMLQuery, new GetCallback<UserAct>() {
                    @Override
                    public void done(UserAct userAct, MLException e) {
                        if (userAct == null || e != null) {
                            if (jqListener != null) {
                                jqListener.onFailure(e.getMessage());
                            }
                        } else {
                            MLDataManager.deleteInBackground(userAct, new DeleteCallback() {
                                @Override
                                public void done(MLException e) {
                                    if (e == null) {
                                        if (jqListener != null) {
                                            jqListener.onFailure(e.getMessage());
                                        }
                                    } else {
                                        if (jqListener != null) {
                                            jqListener.onFailure(e.getMessage());
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onFailure(String errorCode) {
                if (jqListener != null) {
                    jqListener.onFailure(errorCode);
                }
            }
        });

    }
}
