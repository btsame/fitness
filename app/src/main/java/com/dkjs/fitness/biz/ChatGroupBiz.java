package com.dkjs.fitness.biz;

import java.security.acl.Group;
import java.util.List;

/**
 * Created by administrator on 16/8/13.
 */
public class ChatGroupBiz implements IChatGroupBiz{
    @Override
    public void createGroup(String name, List<String> userIds, GroupHandlerListener<Group> listener) {

    }

    @Override
    public void deleteGroup(String groupId, GroupHandlerListener<Void> listener) {

    }

    @Override
    public void addGroupMember(String groupId, String userId, GroupHandlerListener<Void> listener) {

    }

    @Override
    public void removeGroupMember(String groupId, String userId, GroupHandlerListener<Void> listener) {

    }
}
