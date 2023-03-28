package com.triplify.app.group.controller;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.GROUP_HAS_MEMBERS_TABLE;

public class DeleteGroupMemberFromGroupQueryBuilder implements IDeleteGroupMemberFromGroupQueryBuilder{
    @Override
    public String deleteMember(String username, Long group_id) {
        return "DELETE FROM " +GROUP_HAS_MEMBERS_TABLE+ " WHERE `username` = \""+username+ "\" and `group_id` = "+group_id;
    }
}
