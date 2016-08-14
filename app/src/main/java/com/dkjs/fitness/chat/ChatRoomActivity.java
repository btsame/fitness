package com.dkjs.fitness.chat;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.util.URIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.LoadMoreMessagesListener;
import it.slyce.messaging.listeners.UserClicksAvatarPictureListener;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.Message;

/**
 * Created by administrator on 16/8/7.
 */
public class ChatRoomActivity extends FitnessActivity implements UserSendsMessageListener,
        UserClicksAvatarPictureListener, LoadMoreMessagesListener{

    @Bind(R.id.rv_char_users)
    RecyclerView mUsersRV;
    SlyceMessagingFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chatroom);

        ButterKnife.bind(this);

        initChatRoom();
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
        chatFragment.setMoreMessagesExist(true);
    }

    @Override
    public List<Message> loadMoreMessages() {
        return new ArrayList<Message>();
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
