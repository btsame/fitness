package com.dkjs.fitness.chat;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.biz.ChatGroupBiz;
import com.dkjs.fitness.biz.IChatGroupBiz;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.util.URIUtil;
import com.maxleap.im.entity.MessageContent;
import com.maxleap.im.entity.MessageHistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.LoadMoreMessagesListener;
import it.slyce.messaging.listeners.UserClicksAvatarPictureListener;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.MediaMessage;
import it.slyce.messaging.message.Message;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

/**
 * Created by administrator on 16/8/7.
 */
public class ChatRoomActivity extends FitnessActivity implements UserSendsMessageListener,
        UserClicksAvatarPictureListener, LoadMoreMessagesListener{

    public static final String GROUPID_EXTRA = "groupId";

    @Bind(R.id.rv_char_users)
    RecyclerView mUsersRV;
    SlyceMessagingFragment chatFragment;

    IChatGroupBiz chatGroupBiz;
    String groupId;
    long recordTimeStamp;
    List<Message> messageList = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chatroom);

        groupId = getIntent().getStringExtra(GROUPID_EXTRA);
        chatGroupBiz = new ChatGroupBiz();
        Calendar calendar = Calendar.getInstance();
        recordTimeStamp = calendar.getTimeInMillis();


        ButterKnife.bind(this);

        initChatRoom();

        loadChatRecord();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    private void initChatRoom(){
        chatFragment = (SlyceMessagingFragment)getFragmentManager().findFragmentById(R.id.frag_chat);

        chatFragment.setStyle(R.style.chat_room_syle);
        chatFragment.setOnSendMessageListener(this);
        chatFragment.setLoadMoreMessagesListener(this);
        chatFragment.setUserClicksAvatarPictureListener(this);
        chatFragment.setDefaultAvatarUrl(URIUtil.handleNetworkUri(GlobalUserManager.getPortrait()));
        chatFragment.setDefaultUserId(GlobalUserManager.getUserId());
        chatFragment.setPictureButtonVisible(true);
//        chatFragment.setMoreMessagesExist(true);
    }

    public void loadChatRecord(){
        chatGroupBiz.queryChatRecord(groupId, recordTimeStamp, 1000, new IChatGroupBiz.GroupHandlerListener<List<MessageHistory>>() {
            @Override
            public void onSuccess(List<MessageHistory> messageHistories) {
                for(MessageHistory temp : messageHistories){
                    MessageContent content = temp.getContent();
                    if(content.getMedia() == 0){
                        TextMessage message = new TextMessage();
                        message.setUserId(temp.getSpeaker());
                        message.setDate(temp.getTs());
                        if(temp.getSpeaker() == GlobalUserManager.getUserId()){
                            message.setSource(MessageSource.LOCAL_USER);
                        }else{
                            message.setSource(MessageSource.EXTERNAL_USER);
                        }
                        message.setText(content.getBody());
//                        message.setDisplayName();
//                        message.setAvatarUrl();
                        messageList.add(message);
                    }else if(content.getMedia() == 1){
                        MediaMessage message = new MediaMessage();
                        message.setUserId(temp.getSpeaker());
                        message.setDate(temp.getTs());
                        if(temp.getSpeaker() == GlobalUserManager.getUserId()){
                            message.setSource(MessageSource.LOCAL_USER);
                        }else{
                            message.setSource(MessageSource.EXTERNAL_USER);
                        }
                        message.setUrl(content.getBody());
//                        message.setDisplayName();
//                        message.setAvatarUrl();
                        messageList.add(message);
                    }
                }
            }

            @Override
            public void onFailure(String errorCode) {

            }
        });
    }

    @Override
    public List<Message> loadMoreMessages() {

        return messageList;
    }

    @Override
    public void userClicksAvatarPhoto(String s) {

    }

    @Override
    public void onUserSendsTextMessage(String s) {

    }

    @Override
    public void onUserSendsMediaMessage(Uri uri) {

    }
}
