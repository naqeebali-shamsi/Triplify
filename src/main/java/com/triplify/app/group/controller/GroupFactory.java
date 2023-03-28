package com.triplify.app.group.controller;

import com.triplify.app.group.model.GroupDetails;
import com.triplify.app.group.model.GroupMembersDetails;

public class GroupFactory implements IGroupCreationFactory{

    private static GroupFactory groupFactory = null;

    public static IGroupCreationFactory factorySingleton(){
        if (groupFactory == null){
            groupFactory = new GroupFactory();
        }
        return groupFactory;
    }

    @Override
    public GroupDetails makeGroupDetails() {
        return new GroupDetails();
    }

    @Override
    public GroupMembersDetails makeGroupMemberDetails() {
        return new GroupMembersDetails();
    }
}
