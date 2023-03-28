package com.triplify.app.group.controller;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.GROUP_MEMBER_DETAILS_TABLE;

public class GroupMembersDetailsSelectQuery implements IGroupMembersDetailsSelectQuery{
    @Override
    public String selectGroupMemberDetailsQuery() {
        return "SELECT * FROM "+ GROUP_MEMBER_DETAILS_TABLE;
    }
}
